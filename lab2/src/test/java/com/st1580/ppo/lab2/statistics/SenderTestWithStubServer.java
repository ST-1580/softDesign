package com.st1580.ppo.lab2.statistics;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.function.Consumer;

import com.xebialabs.restito.server.StubServer;
import org.glassfish.grizzly.http.Method;
import org.json.JSONObject;
import org.junit.Test;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.stringContent;
import static com.xebialabs.restito.semantics.Condition.method;
import static com.xebialabs.restito.semantics.Condition.startsWithUri;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class SenderTestWithStubServer {
    private static final int PORT = 8080;

    private void withStubServer(int port, Consumer<StubServer> callback) {
        StubServer stubServer = null;
        try {
            stubServer = new StubServer(port).run();
            callback.accept(stubServer);
        } finally {
            if (stubServer != null) {
                stubServer.stop();
            }
        }
    }

    @Test
    public void checkSender() {
        withStubServer(PORT, s -> {
            whenHttp(s)
                    .match(method(Method.GET), startsWithUri("/statisticsReddit"))
                    .then(stringContent("{ 'data': [1, 2, 3]}"));

            URI uri = null;
            try {
                uri = new URI("http://localhost:" + PORT + "/statisticsReddit");
            } catch (URISyntaxException e) {
                fail();
            }

            int actualResponseLen = new StatisticsServiceImpl().send(uri).getJSONArray("data").length();
            int expectedResponseLen = 3;

            assertEquals(expectedResponseLen, actualResponseLen);
        });
    }
}

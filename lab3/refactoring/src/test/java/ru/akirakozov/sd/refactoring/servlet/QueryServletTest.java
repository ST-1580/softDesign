package ru.akirakozov.sd.refactoring.servlet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;
import ru.akirakozov.sd.refactoring.utils.TestUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.akirakozov.sd.refactoring.utils.DatabaseUtils.*;

public class QueryServletTest {
    QueryServlet queryServlet;
    HttpServletRequest requestMock;
    HttpServletResponse responseMock;
    StringWriter stringWriter;
    Map<String, String> header2Command = new HashMap<>();

    @Before
    public void init() throws IOException, SQLException {
        createTable(TestUtils.TEST_DB);

        requestMock = mock(HttpServletRequest.class);
        responseMock = mock(HttpServletResponse.class);

        updatePrinter();

        queryServlet = new QueryServlet();

        if (header2Command.isEmpty()) {
            header2Command.put("max", "<h1>Product with max price: </h1>\n");
            header2Command.put("min", "<h1>Product with min price: </h1>\n");
            header2Command.put("sum", "Summary price: \n");
            header2Command.put("count", "Number of products: \n");
        }
    }

    private void updatePrinter() throws IOException {
        stringWriter = new StringWriter();
        when(responseMock.getWriter()).thenReturn(new PrintWriter(stringWriter));
    }

    @After
    public void clear() throws SQLException {
        dropTable(TestUtils.TEST_DB);
    }

    @Test
    public void queriesFromEmptyTable() throws IOException, SQLException {
        performQuery("max", "\t0</br>\n");
        performQuery("min", "\t0</br>\n");
        performQuery("sum", "0\n");
        performQuery("count", "0\n");
    }

    @Test
    public void getFromFilledTable() throws SQLException, IOException {
        try {
            addItem(TestUtils.TEST_DB, "a", 1L);
            addItem(TestUtils.TEST_DB, "b", 2L);
            addItem(TestUtils.TEST_DB, "c", 3L);
        } catch (SQLException e) {
            System.out.println("Fail when filling table " + e.getMessage());
        }

        performQuery("max", "c\t3</br>\n");
        performQuery("min", "a\t1</br>\n");
        performQuery("sum", "6\n");
        performQuery("count", "3\n");
    }

    private void performQuery(final String queryCommand, final String expectedAnswer) throws IOException, SQLException {
        when(requestMock.getParameter("command")).thenReturn(queryCommand);

        queryServlet.doGet(requestMock, responseMock);

        Assert.assertEquals(stringWriter.toString(),
                TestUtils.constructHtml(header2Command.get(queryCommand) + expectedAnswer));

        updatePrinter();
    }
}
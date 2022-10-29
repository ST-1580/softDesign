package ru.akirakozov.sd.refactoring.servlet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.akirakozov.sd.refactoring.utils.TestUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.akirakozov.sd.refactoring.utils.DatabaseUtils.addItem;
import static ru.akirakozov.sd.refactoring.utils.DatabaseUtils.createTable;
import static ru.akirakozov.sd.refactoring.utils.DatabaseUtils.dropTable;

public class GetProductServletTest {
    GetProductsServlet getProductsServlet;
    HttpServletRequest requestMock;
    HttpServletResponse responseMock;
    StringWriter stringWriter;

    @Before
    public void init() throws IOException, SQLException {
        createTable(TestUtils.TEST_DB);

        requestMock = mock(HttpServletRequest.class);
        responseMock = mock(HttpServletResponse.class);

        stringWriter = new StringWriter();
        when(responseMock.getWriter()).thenReturn(new PrintWriter(stringWriter));

        getProductsServlet = new GetProductsServlet();
    }

    @After
    public void clear() throws SQLException {
        dropTable(TestUtils.TEST_DB);
    }

    @Test
    public void getFromEmptyTable() {
        try {
            getProductsServlet.doGet(requestMock, responseMock);
        } catch (IOException ex) {
            System.out.println("GetProducts failed with IO exception " + ex.getMessage());
        }

        Assert.assertEquals(stringWriter.toString(), TestUtils.constructHtml(""));
    }

    @Test
    public void getFromFilledTable() throws SQLException {
        addItem(TestUtils.TEST_DB, "a", 1L);
        addItem(TestUtils.TEST_DB, "b", 2L);
        addItem(TestUtils.TEST_DB, "c", 3L);

        try {
            getProductsServlet.doGet(requestMock, responseMock);
        } catch (IOException ex) {
            System.out.println("GetProducts failed with IO exception " + ex.getMessage());
        }

        Assert.assertEquals(stringWriter.toString(), TestUtils.constructHtml(
                "a\t1</br>\nb\t2</br>\nc\t3</br>\n"
        ));
    }
}

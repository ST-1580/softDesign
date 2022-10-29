package ru.akirakozov.sd.refactoring.servlet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.akirakozov.sd.refactoring.utils.DatabaseUtils;
import ru.akirakozov.sd.refactoring.utils.Item;
import ru.akirakozov.sd.refactoring.utils.TestUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.akirakozov.sd.refactoring.utils.DatabaseUtils.createTable;
import static ru.akirakozov.sd.refactoring.utils.DatabaseUtils.dropTable;

public class AddProductServletTest {
    AddProductServlet addProductServlet;
    HttpServletRequest requestMock;
    HttpServletResponse responseMock;
    StringWriter stringWriter;

    @Before
    public void init() throws IOException, SQLException {
        createTable(TestUtils.TEST_DB);

        requestMock = mock(HttpServletRequest.class);
        responseMock = mock(HttpServletResponse.class);

        when(requestMock.getParameter("name")).thenReturn("testName");
        when(requestMock.getParameter("price")).thenReturn("228");

        stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(responseMock.getWriter()).thenReturn(printWriter);

        addProductServlet = new AddProductServlet();
    }

    @After
    public void clear() throws SQLException {
        dropTable(TestUtils.TEST_DB);
    }

    @Test
    public void addProductToTablePing() {
        try {
            addProductServlet.doGet(requestMock, responseMock);
        } catch (IOException ex) {
            System.out.println("AddProducts failed with IO exception " + ex.getMessage());
        }

        Assert.assertEquals(stringWriter.toString(), "OK\n");
    }


    @Test
    public void checkAddProductToTable() {
        try {
            addProductServlet.doGet(requestMock, responseMock);
        } catch (IOException ex) {
            System.out.println("AddProducts failed with IO exception " + ex.getMessage());
        }

        List<Item> foundedItems = DatabaseUtils.getItems(TestUtils.TEST_DB);
        Assert.assertEquals(foundedItems.size(), 1);

        Item foundedItem = foundedItems.get(0);
        Assert.assertEquals(foundedItem.getName(), "testName");
        Assert.assertEquals(foundedItem.getPrice(), 228L);

        Assert.assertEquals(stringWriter.toString(), "OK\n");
    }

}

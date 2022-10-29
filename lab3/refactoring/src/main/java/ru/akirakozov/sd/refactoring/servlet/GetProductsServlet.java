package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import ru.akirakozov.sd.refactoring.utils.DatabaseUtils;
import ru.akirakozov.sd.refactoring.utils.HtmlUtils;
import ru.akirakozov.sd.refactoring.utils.Item;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final List<Item> items = DatabaseUtils.getItems(DatabaseUtils.PROD_DB);
        String resultedHtml = HtmlUtils.convertItems(items);

        response.getWriter().print(resultedHtml);
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

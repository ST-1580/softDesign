package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import ru.akirakozov.sd.refactoring.utils.DatabaseUtils;
import ru.akirakozov.sd.refactoring.utils.HtmlUtils;
import ru.akirakozov.sd.refactoring.utils.Item;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");
        String result = "Unknown command: " + command;

        if ("max".equals(command)) {
            Item maxItem = DatabaseUtils.getMaxItem(DatabaseUtils.PROD_DB);
            result = HtmlUtils.convertMax(maxItem);
        } else if ("min".equals(command)) {
            Item minItem = DatabaseUtils.getMinItem(DatabaseUtils.PROD_DB);
            result = HtmlUtils.convertMin(minItem);
        } else if ("sum".equals(command)) {
            int sum = DatabaseUtils.getSum(DatabaseUtils.PROD_DB);
            result = HtmlUtils.convertSum(sum);
        } else if ("count".equals(command)) {
            int count = DatabaseUtils.getCount(DatabaseUtils.PROD_DB);
            result = HtmlUtils.convertCount(count);
        }

        response.getWriter().print(result);
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}

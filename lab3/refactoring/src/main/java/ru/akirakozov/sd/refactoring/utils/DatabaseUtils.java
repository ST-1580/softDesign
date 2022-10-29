package ru.akirakozov.sd.refactoring.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtils {
    public static Connection connect(String dbName) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    public static void createTable(final String dbName) throws SQLException {
        try (Statement statement = connect(dbName).createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          INT     NOT NULL)";
            statement.executeUpdate(sql);
        }
    }

    public static void dropTable(final String dbName) throws SQLException {
        try (Statement statement = connect(dbName).createStatement()) {
            String sql = "DROP TABLE PRODUCT";
            statement.executeUpdate(sql);
        }
    }

    public static void addItem(final String dbName, final String name, final Long price) throws SQLException {
        try (Statement statement = connect(dbName).createStatement()) {
            String sql = "INSERT INTO PRODUCT " +
                    "(NAME, PRICE) VALUES (\"" + name + "\"," + price.toString() + ")";
            statement.executeUpdate(sql);
        }
    }
}
package ru.akirakozov.sd.refactoring.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtils {
    public static final String PROD_DB = "jdbc:sqlite:test.db";

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

    public static List<Item> getItems(final String dbName) {
        final List<Item> items = new ArrayList<>();
        try (Statement statement = connect(dbName).createStatement()) {
            String sql = "SELECT * FROM PRODUCT";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String name = rs.getString("name");
                long price = rs.getLong("price");
                items.add(new Item(name, price));
            }

            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return items;
    }

    private static Item getItemFromQuery(String dbName, String query) {
        Item res = new Item("", 0L);
        try (Statement statement = connect(dbName).createStatement()) {
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                String name = rs.getString("name");
                long price = rs.getLong("price");
                res = new Item(name, price);
            }

            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return res;
    }

    public static Item getMaxItem(final String dbName) {
        return getItemFromQuery(dbName, "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");
    }

    public static Item getMinItem(final String dbName) {
        return getItemFromQuery(dbName, "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
    }

    private static int getIntFromQuery(String dbName, String query) {
        int res = 0;
        try (Statement statement = connect(dbName).createStatement()) {
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                res = rs.getInt(1);
            }

            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return res;
    }

    public static int getSum(final String dbName) {
        return getIntFromQuery(dbName, "SELECT SUM(price) FROM PRODUCT");
    }

    public static int getCount(final String dbName) {
        return getIntFromQuery(dbName, "SELECT COUNT(*) FROM PRODUCT");
    }
}
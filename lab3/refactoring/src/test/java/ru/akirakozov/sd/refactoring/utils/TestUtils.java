package ru.akirakozov.sd.refactoring.utils;

import java.util.List;

public class TestUtils {
    public static final String TEST_DB = "jdbc:sqlite:test.db";

    public static String constructHtml(String body) {
        return "<html><body>\n" + body + "</body></html>\n";
    }
}

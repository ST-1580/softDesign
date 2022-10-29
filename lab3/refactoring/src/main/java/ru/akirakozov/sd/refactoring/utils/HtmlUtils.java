package ru.akirakozov.sd.refactoring.utils;

import java.util.List;

public class HtmlUtils {

    public static String convertItems(List<Item> items) {
        StringBuilder res = new StringBuilder();

        res.append("<html><body>").append("\n");
        for (Item item : items) {
            res.append(item.getName()).append("\t").append(item.getPrice()).append("</br>").append("\n");
        }
        res.append("</body></html>").append("\n");

        return res.toString();
    }

    public static String covertItem(Item item, String header) {
        StringBuilder res = new StringBuilder();

        res.append("<html><body>").append("\n");
        res.append(header).append("\n");
        res.append(item.getName()).append("\t").append(item.getPrice()).append("</br>").append("\n");
        res.append("</body></html>").append("\n");

        return res.toString();
    }

    public static String convertMax(Item item) {
        return covertItem(item, "<h1>Product with max price: </h1>");
    }

    public static String convertMin(Item item) {
        return covertItem(item, "<h1>Product with min price: </h1>");
    }

    public static String covertInt(int value, String header) {
        StringBuilder res = new StringBuilder();
        res.append("<html><body>").append("\n");
        res.append(header).append("\n");
        res.append(value).append("\n");
        res.append("</body></html>").append("\n");

        return res.toString();
    }

    public static String convertSum(int sum) {
        return covertInt(sum, "Summary price: ");
    }

    public static String convertCount(int count) {
        return covertInt(count, "Number of products: ");
    }
}

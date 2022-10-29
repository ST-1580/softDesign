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

    public static String covertMaxOrMin(Item item, boolean isMax) {
        StringBuilder res = new StringBuilder();

        res.append("<html><body>").append("\n");
        res.append("<h1>Product with ");
        res.append(isMax ? "max" : "min");
        res.append(" price: </h1>").append("\n");
        res.append(item.getName()).append("\t").append(item.getPrice()).append("</br>").append("\n");
        res.append("</body></html>").append("\n");

        return res.toString();
    }

    public static String convertMax(Item item) {
        return covertMaxOrMin(item, true);
    }

    public static String convertMin(Item item) {
        return covertMaxOrMin(item, false);
    }

    public static String covertSumOrCount(int value, boolean isSum) {
        StringBuilder res = new StringBuilder();
        res.append("<html><body>").append("\n");
        res.append(isSum ? "Summary price: " : "Number of products: ").append("\n");
        res.append(value).append("\n");
        res.append("</body></html>").append("\n");

        return res.toString();
    }

    public static String convertSum(int sum) {
        return covertSumOrCount(sum, true);
    }

    public static String convertCount(int count) {
        return covertSumOrCount(count, false);
    }
}

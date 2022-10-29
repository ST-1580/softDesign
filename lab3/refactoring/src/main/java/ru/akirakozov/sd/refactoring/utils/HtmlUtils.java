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
}

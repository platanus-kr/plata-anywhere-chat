package org.platanus.platachat.message.utils;

public class XSSFilter {
    public static String filterXSS(final String inputString) {
        String outputString = inputString;
        outputString = outputString.replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                .replaceAll("\"", "&quot;")
                .replaceAll("'", "&#x27;")
                .replaceAll("/", "&#x2F;")
                .replaceAll("%", "&#37;")
                .replaceAll("%2F", "");
        return outputString;
    }
}

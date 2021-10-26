package jPlus.util;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class StringUtils {

    public static boolean isNullWhiteSpaceOrEmpty(String... strings) {
        boolean anyAreNull = false;
        for (String s : strings) anyAreNull = s == null || s.isEmpty() || s.trim().isEmpty();
        return anyAreNull;
    }

    public static boolean hasContent(String... strings) {
        return !isNullWhiteSpaceOrEmpty(strings);
    }

    public static String capitalizeAllWords(String s) {
        return Arrays.stream(s.toLowerCase().split("\\s+"))
                .map(t -> t.substring(0, 1).toUpperCase() + t.substring(1))
                .collect(Collectors.joining(" "));
    }

    public static String lowerCaseFirstLetter(String str) {
        if (str == null || str.length() == 0) return "";
        if (str.length() == 1) return str.toLowerCase();

        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }
}
package jPlus.util;

import java.util.Arrays;

public class ConsoleUtils {
    public static final char STANDARD_BANNER = '*';

    public static String encaseInBanner(String s) {
        return encaseInBanner(s, STANDARD_BANNER);
    }

    public static String encaseInBanner(String s, int spacing) {
        return encaseInBanner(s, STANDARD_BANNER, spacing, spacing);
    }

    public static String encaseInBanner(String s, int aboveSpace, int belowSpace) {
        return encaseInBanner(s, STANDARD_BANNER, aboveSpace, belowSpace);
    }

    public static String encaseInBanner(String s, char bannerComp) {
        return encaseInBanner(s, bannerComp, 0, 0);
    }

    public static String encaseInBanner(String s, char bannerComp, int aboveSpace, int belowSpace) {
        String bannerCompS = Character.toString(bannerComp);
        final StringBuilder sb = new StringBuilder();
        sb.append(sep().repeat(Math.max(0, aboveSpace)));
        sb.append(banner(bannerComp, s.length() + 2));
        sb.append(sep());
        sb.append(bannerComp);
        sb.append(s);
        sb.append(bannerComp);
        sb.append(sep());
        sb.append(banner(bannerComp, s.length() + 2));
        sb.append(sep().repeat(Math.max(0, belowSpace)));

        return sb.toString();
    }

    public static String banner(int bannerCount) {
        return banner(STANDARD_BANNER, bannerCount);
    }

    public static String banner(char bannerComp, int bannerCount) {
        final char[] bannerC = new char[bannerCount];
        Arrays.fill(bannerC, bannerComp);
        return new String(bannerC);
    }

    public static String sep() {
        return System.lineSeparator();
    }
}

package jPlus;

import jPlus.callback.Receivable1;

/**
 * Hey! It's Marcus.
 * Welcome to JPlus! There's lots of fun stuff here
 * that can help you build your Java application.
 * Mostly stuff for saving code lines and effort,
 * but also some really well refined utils that
 * are pretty reliable! Though, stay away
 * from Resources.extractTempFiles for now. :(
 * Also try out Thread commands. They work
 * at basic impl, but haven't really been
 * used much.
 */
public class JPlus {
    public static Receivable1<String> logger;

    public static void log(String s) {
        if (logger != null) logger.receive(s);
    }
}

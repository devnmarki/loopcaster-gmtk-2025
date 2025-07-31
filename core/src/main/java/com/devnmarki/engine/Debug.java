package com.devnmarki.engine;

public class Debug {

    public static void log(Object message) {
        System.out.println("[" + getClassName() + "] " + message);
    }

    public static void log(Object... messages) {
        System.out.print("[" + getClassName() + "] ");
        for (Object msg : messages) {
            System.out.print(msg + " ");
        }
        System.out.println();
    }

    public static void error(Object message) {
        System.err.println("[" + getClassName() + "] " + message);
    }

    public static void error(Object... messages) {
        System.err.print("[" + getClassName() + "] ");
        for (Object msg : messages) {
            System.err.print(msg + " ");
        }
        System.out.println();
    }

    private static String getClassName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        if (stackTrace.length >= 4) {
            String fullClassName = stackTrace[3].getClassName();
            return fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        }

        return "Unknown";
    }

}

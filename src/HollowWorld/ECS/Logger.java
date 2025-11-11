package HollowWorld.ECS;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static boolean logToFile = false;
    private static boolean enabled  = true;

    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";

    private static PrintWriter fileWriter;

    // Wenn ihr auf eine Datei anstatt in die Konsole Loggen Wollt in Main beide methoden auskommentieren
    // Löscht bitte die Logs wenn sie nicht super wichtig sind. Zum Debuggen Generell nich verwenden
    public static void logToFile() {
        if (!enabled) return;
        logToFile = true;

        try {
            File logDir = new File("res/logs");
            if (!logDir.exists()) logDir.mkdirs();

            String fileName = "res/logs/Log_" +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) +
                    ".txt";

            fileWriter = new PrintWriter(new FileWriter(fileName, false), true);
            System.out.println(GREEN + "[Logger] Writing to " + fileName + RESET);
        } catch (IOException e) {
            System.err.println(RED + "[Logger] Failed to create log file: " + e.getMessage() + RESET);
            logToFile = false;
        }

    }

    public static void shutdown() {
        if (fileWriter != null) {
            fileWriter.close();
        }
    }


    // Dass sind die Log methoden die man dann aufruft

    public static void info(String msg) {
        if (!enabled) return;
        log("INFO", msg, null, GREEN);
    }

    public static void warn(String msg) {
        if (!enabled) return;
        log("WARN", msg, null, YELLOW);
    }

    public static void error(String msg) {
        if (!enabled) return;
        log("ERROR", msg, null, RED);
    }

    public static void error(String msg, Throwable t) {
        if (!enabled) return;
        log("ERROR", msg, t, RED);
    }



    private static void log(String level, String msg, Throwable t, String color) {
        if (!enabled) return;
        String time = LocalDateTime.now().format(FORMAT);
        StackTraceElement caller = Thread.currentThread().getStackTrace()[3];
        String location = caller.getFileName() + ":" + caller.getLineNumber();

        String output = String.format("[%s] [%s] (%s) %s", level, time, location, msg);

        // Konsole farbig
        System.out.println(color + output + RESET);
        if (t != null) t.printStackTrace(System.out);

        // Datei-Logging
        if (logToFile && fileWriter != null) {
            fileWriter.println(output);
            if (t != null) t.printStackTrace(fileWriter);
        }
    }

}
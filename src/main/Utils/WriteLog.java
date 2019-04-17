package main.Utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class WriteLog {

    private static WriteLog instance = null;

    private final String SESSION;
    private static FileHandler handler = null;
    private final String path;

    private WriteLog () {
        instance = this;
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        SESSION = f.format(now);
        path = buildPath();
        handler = fileHandler();
    }


    public static WriteLog of() {
        if (instance == null) {
            synchronized (WriteLog.class) {
                if (instance == null) {
                    return new WriteLog();
                }
            }
        }
        return instance;
    }


   public static void addHandler (Logger logger) {
        if (instance != null && handler != null) {
            logger.addHandler(handler);
        }
   }

   private FileHandler fileHandler () {
        try {
            System.out.println(path);
            FileHandler mHandler = new FileHandler(path);
            SimpleFormatter formatter = new SimpleFormatter();
            mHandler.setFormatter(formatter);
            return mHandler;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
   }

   public static void close () {
        if (handler != null) handler.close();
   }


    private String buildPath () {
        StringBuilder builder = new StringBuilder();
        builder.append("src/main/Logs/");
        builder.append(SESSION);
        builder.append(".txt");
        return builder.toString();
    }

}

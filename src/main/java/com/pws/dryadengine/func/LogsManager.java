package com.pws.dryadengine.func;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class LogsManager {
  public static final String logFileCreationTime = getLogDateTime();
  public static final String logFolder = "data/logs/";
  public static final String logFileHead = logFolder + "log_" + logFileCreationTime + ".log";

  static {
    createLogFile();
  }

  public static String getLogDateTime() {
    String time = "" + LocalTime.now().getHour() + LocalTime.now().getMinute() + LocalTime.now().getSecond();
    String date = LocalDate.now().toString().replace("-", "");
    return date + "_" + time;
  }

  public static void createLogFile() {
    File f = FileManager.createFile(logFileHead);
    FileManager.writeToFile(f, FileManager.getLocalRoute() + logFileHead + "\n", true);
    FileManager.writeToFile(f, LocalDateTime.now().toString() + "\n", true);
  }

  public static void deleteAllFiles() {
    String data = FileManager.readFromFile(logFileHead);
    for (File fileEntry : new File(FileManager.getLocalRoute() + logFolder).listFiles()) {
      FileManager.deleteFile(logFolder + fileEntry.getName());
    }
    File f = FileManager.createFile(logFileHead);
    FileManager.writeToFile(f, data, false);
    Debug.println("Succesfuly deeleted old logs.");
  }

  public static void log(String data) {
    try {
      FileWriter writer = new FileWriter(FileManager.getLocalRoute() + logFileHead, true);
      writer.write(data);
      writer.close();
    } catch (Exception e) {
      Debug.logError(e);
    }
  }
}

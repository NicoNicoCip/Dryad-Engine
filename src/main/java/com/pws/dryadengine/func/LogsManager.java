package com.pws.dryadengine.func;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class LogsManager {
  public static final String logFileCreationTime = getLogDateTime();
  public static final String logFolder = "data/logs/";
  public static final String logFileHead = logFolder + "log_" + logFileCreationTime + ".log";

  static {
    createLogFile();
  }

  public static void createLogFile() {
    File f = FileManager.createFile(logFileHead);
    FileManager.updateFile(f,"\n", true);
  }

  public static String getLogDateTime() {
    String time = LocalDateTime.now().toString().substring(11, 19).replace(":", "");
    String date = LocalDate.now().toString().replace("-", "");
    return date + "_" + time;
  }

  public static void deleteAllFiles() {
    String data = FileManager.readFile(logFileHead);
    for (File fileEntry : new File(FileManager.getProjectPath() + logFolder).listFiles()) {
      FileManager.deleteFile(logFolder + fileEntry.getName());
    }
    File f = FileManager.createFile(logFileHead);
    FileManager.updateFile(f, data, false);
    Debug.println("Succesfuly deeleted old logs.");
  }

  public static void log(String data) {
    try {
      FileWriter writer = new FileWriter(FileManager.getProjectPath() + logFileHead, true);
      writer.write(data);
      writer.close();
    } catch (Exception e) {
      Debug.logError(e);
    }
  }
}

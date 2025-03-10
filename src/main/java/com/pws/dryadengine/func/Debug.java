package com.pws.dryadengine.func;

import java.io.PrintWriter;
import java.io.StringWriter;

public abstract class Debug {
  public static final <T> void print(T args) {
    LogsManager.log(args.toString());
    System.out.print(args);
  }

  public static final <T> void println(T args) {
    LogsManager.log(args.toString() + "\n");
    System.out.println(args);
  }

  public static final void logError(Exception e) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    e.printStackTrace(pw);
    String stackTrace = "\n" + sw.toString();
    println(stackTrace);
  }

  public static final <T> void log(T args) {
    LogsManager.log(args.toString());
  }

  public static final <T> void out(T args) {
    System.out.print(args);
  }
}

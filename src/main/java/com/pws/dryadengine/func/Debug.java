package com.pws.dryadengine.func;

import java.io.PrintWriter;
import java.io.StringWriter;

public abstract class Debug {
  public static final <T> void print(T toPrint) {
    LogsManager.log(toPrint.toString());
    System.out.print(toPrint.toString());
  }

  public static final <T> void println(T toPrint) {
    LogsManager.log(toPrint.toString() + "\n");
    System.out.println(toPrint);
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
    System.out.print(args.toString());
  }
}

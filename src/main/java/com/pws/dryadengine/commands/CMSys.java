package com.pws.dryadengine.commands;

import java.io.File;
import java.util.Arrays;

import com.pws.dryadengine.core.App;
import com.pws.dryadengine.core.commands.Command;
import com.pws.dryadengine.func.Debug;

public class CMSys extends Command {
  @Override
  public void setCommand() {
    this.command = "sys";
  }

  @Override
  public void setOptions() {
    this.options = new String[] {
        "clear",
        "restart",
        "exit"
    };
  }

  @Override
  public void construct() {
    registerOptions((args) -> sysClear(), "clear");
    registerOptions((args) -> sysRestart(), "restart");
    registerOptions((args) -> sysExit(), "exit");
  }

  @Override
  public boolean run(String[] args) {
    if (args.length == 0) {
      Debug.print("No subcommand present.");
      return false;
    }
    runOptions(args);
    return true;
  }

  public void sysClear() {
    Debug.out("\033[2J\033[HCleared! ");
    Debug.log("Cleared the screen.");
  }

  public void sysRestart() {
    // First close your threads gracefully
    App.backend.interrupt();
    App.frontend.interrupt();
    
    try {
      // Wait for threads to finish
      App.backend.join();
      App.frontend.join();

      // Restart the application using ProcessBuilder
      ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "start", "BuildAndRun.bat");
      processBuilder.directory(new File(System.getProperty("user.dir")));
      Process process = processBuilder.start();
    } catch (Exception e) {
      Debug.logError(e);
    }

    // Exit current JVM instance
    System.exit(0);
  }

  public void sysExit() {
    App.finnishExecution = true;
  }

  @Override
  public void setHelp() {
    this.help = new CIHelp(
        "sys (system command)",
        "sys", "-- manages the system.",
        "manages the system in different ways. unlike other commands, this one does nothing by default and needs an auxiliary command "
            +
            "to be given as a parameter to do something.",
        Arrays.asList(
            "clear",
            "restart",
            "exit"),
        Arrays.asList(
            "clears the terminal",
            "restarts the current process.",
            "exits the current terminal porcess."),
        "More auxiliary commands comming soon.",
        "Finn Willow",
        "FOSS (Temporary)");
  }
}

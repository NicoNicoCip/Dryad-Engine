package com.pws.dryadengine.commands;

import java.io.File;
import java.util.Arrays;

import com.pws.dryadengine.core.App;
import com.pws.dryadengine.core.commands.Command;
import com.pws.dryadengine.func.Debug;

public class CMSys extends Command {
  @Override
  public void construct() {
    this.command = "sys";
    this.options = new String[] {
        "clear",
        "exit"
    };
    registerOptions((args) -> sysClear(), "clear");
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
    try {
      new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    } catch (Exception e) {
      Debug.logError(e);
    }
    Debug.print("--------------- Cleared the screen ------------------");
  }

  public void sysExit() {
    App.finnishExecution = true;
    Debug.print("If you're seeing this then you can either press Enter to restart the engine, or re-run the proper batch.");
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
            "exit"),
        Arrays.asList(
            "clears the terminal",
            "exits the current terminal porcess."),
        "More auxiliary commands comming soon.",
        "Finn Willow",
        "FOSS (Temporary)");
  }
}

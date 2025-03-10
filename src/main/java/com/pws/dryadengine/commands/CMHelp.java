package com.pws.dryadengine.commands;

import java.lang.reflect.Array;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.pws.dryadengine.core.App;
import com.pws.dryadengine.core.commands.Command;
import com.pws.dryadengine.func.Debug;

public class CMHelp extends Command {
  private boolean hasOption = false;
  @Override
  public void setCommand() {
    command = "help";
  }

  @Override
  public void setOptions() {
    options = new String[] {
      "-f", "--full",
      "-c", "--commands",
      "-o", "--options",
    };
  }

  @Override
  public void construct() {
    registerOptions((args) -> helpFull(),"-f","--full");
    registerOptions((args) -> helpAllCommands(),"-c","--commands");
    registerOptions((args) -> helpCommandOptions(args), "-o", "--options");
  }

  @Override
  public boolean run(String[] args) {
    String[] filtered = matching(getOptions(), args);
    for (String option : filtered) {
      executeOption(option, args);
    }

    if(hasOption) return true;

    return help(args);
  }

  public void helpFull() {
    List<Command> com = App.commandMan.getCommands();
    int comSize = com.size();
    for (short i = 0; i < comSize; i++) {
      Debug.print(com.get(i).getCommand() + "\t");
      helpCommandOptions(new String[] {"-o" ,com.get(i).getCommand()});
      Debug.print(i == com.size()-1? "" : "\n");
    }
    hasOption = true;
  }

  public void helpAllCommands() {
    for (Command com : App.commandMan.getCommands()) {
      Debug.print(com.getCommand() + "\t");
    }

    hasOption = true;
  }

  public void helpCommandOptions(String[] args) {
    if(args.length != 2) {
      Debug.print("Wrong number of arguments. The sintaxis is \"help + -o or --options + command\" and nothing more.");
      hasOption = true;
      return;
    }

    for (String opt : App.commandMan.getOneCommand(args[1]).getOptions()) {
      Debug.print(opt + "\t");
    }

    hasOption = true;
  }

  public boolean help(String[] args) {
    if(args.length == 0) {
      this.help.print();
      return true;
    }

    Set<String> soccargs = new HashSet<>();
    soccargs.addAll(Arrays.asList(args));
    Object[] finalArgs = soccargs.toArray();

    for (short i = 0; i < finalArgs.length; i++) {
      if(App.commandMan.getOneCommand(finalArgs[i].toString()) == null) {
        Debug.print("Command \"" + finalArgs[i] + "\" not found.");
        continue;
      }

      App.commandMan.getOneCommand(finalArgs[i].toString()).getHelp().print();

      if(i != finalArgs.length - 1)
          Debug.println("");
    }
    return true;
  }

  @Override
  public void setHelp() {
    this.help = new CIHelp(
      "help [options]", 
      "help", "-- [option for type of help or nothing for this text]", 
      "a command that lets you see any help text of any command, or if you dont know something about a command you can " +
      "use one of the options.",
        Arrays.asList(
          "help",
          "-f or --full",
          "-c or --commands",
          "-o or --options"
        ),
        Arrays.asList(
          "prints this text if no arguments given, or prints the help of the command or commands given.",
          "prints all the commands and the options they have.",
          "prints the commands installed in the engine.",
          "prints just the options of the given command."
        ),
      "",
      "Finn Willow",
      "FOSS (Temporary)"
        );
  }
}

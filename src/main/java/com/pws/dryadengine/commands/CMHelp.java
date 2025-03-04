package com.pws.dryadengine.commands;

import java.util.Arrays;
import java.util.List;

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
    for (short i = 0; i < com.size(); i++) {
      Debug.print(com.get(i).getCommand() + "\t");
      helpCommandOptions(new String[] {"help", "-o" ,com.get(i).getCommand()});
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
    if(args.length != 3) {
      Debug.print("Wrong number of arguments. The sintaxis is \"help + -o or --options + command\" and nothing more.");
      hasOption = true;
      return;
    }

    for (String opt : App.commandMan.getOneCommand(args[2]).getOptions()) {
      Debug.print(opt + "\t");
    }

    hasOption = true;
  }

  public boolean help(String[] args) {
    if(args.length == 1) {
      this.help.print();
      return true;
    }

    for (byte i = 1; i < args.length; i++) {
      if(App.commandMan.getOneCommand(args[i]) == null) {
        Debug.print("Command \"" + args[i] + "\" not found.");
        continue;
      }
      App.commandMan.getOneCommand(args[i]).getHelp().print();
    }
    return true;
  }

  @Override
  public void setHelp() {
    String text = """
      [STARTOFHELP - help]           
      .  help [--option/s] (String ... argumnets)
      .
      .  [NAME]
      .     help -- a usefull command for informing yourself on any command you want.
      .
      .  [SYNOPSIS]
      .     "help" has one or more options that can be put anywhere, and that help with specifying what kind of help you
      .     want to see. note, that options for this command are blocking, and any command after the option (excluding 
      .     redirecting and pipes) will not be ran. if written on its own, prints this text, and will print the help text
      .     of all the arguments after.
      .
      .  [DESCRIOPTION]
      .    help             |> prints this text when alone, and prints all the helps of all the specified commands, when not. 
      .    -a or --all      |> prints all the helps of all the commands curently installed.
      .    -c or --commands |> prints all of the commands on their own in a line.
      . 
      .  [AUTHOR]
      .    Finn Willow
      [ENDOFHELP]""";
    this.help = new CIHelp(text);
  }
}

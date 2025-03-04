package com.pws.dryadengine.commands;

import java.util.Arrays;

import com.pws.dryadengine.func.Debug;
import com.pws.dryadengine.core.commands.Command;


public class CMEcho extends Command {

  @Override
  public void setCommand() {
    this.command = "echo";
  }

  @Override
  public void setOptions() {
    this.options = new String[] {
      "-l", "--log",
      "-h", "--help"
    };
  }

  @Override
  public void construct() {
    registerOptions((args) -> logEcho(args),"-l","--log");
    registerOptions((args) -> helpEcho(),"-h","--help");
  }

  @Override
  public boolean run(String[] args) {
    String[] filtered = matching(getOptions(), args);
    for (String option : filtered) {
      executeOption(option, args);
    }

    return true;
  }

  private void echo(String[] args) {
    short log = getPair("-l", "--log", args);
    short help = getPair("-h", "--help", args);
    StringBuffer sb = new StringBuffer();
    
    for (short i = 1; i < args.length; i++) {
      if((log != -1 && (i == log || i == log + 1)) || (help != -1 && i == help)) continue;
      args[i] = args[i].replace("\\", "");
      
      if(i != 1 && i != args.length -1) sb.append("\n");
      sb.append(args[i]);
    }
    if(sb.toString() != "") Debug.print(sb.toString());
  }

  private void logEcho(String[] args) {
    short position = getPair("-l", "--log", args);
    if(position + 1 >= args.length) {
      Debug.print("ERROR: no string found after -l or --log");
      return;
    }
    Debug.log(args[position + 1]);
    if(!args[position-1].equals("echo")) Debug.print("\n");
    Debug.out("Logged: " + args[position + 1]);
  }

  private void helpEcho() {
    this.help.print();
  }

  @Override
  public void setHelp() {
    this.help = new CIHelp("echo [--option/s] (String ... arguments)",
    "echo", "-- prints the arguments to the therminal.",
    "echo [option or options to print; can be anywhere] (String or strings of text to be printed)"+
    "!!! dont actually add the parentesis. each part just needs to be separated by a space."+
    "!!! if you want to use spaces in the string, you can eitehr use \"\", '', or \\. these will be explained lower.",
    Arrays.asList(
      "echo", 
      "-h or --help", 
      "-l or --log", 
      "String ... args", 
      "\" \"", 
      "' '", 
      "\\+space"
      ),
    Arrays.asList(
      "prints the strings that are not part of other options.",
      "prints this text.",
      "prints the next string to the end log file. you will never be allowed to completely override the log file. "+
        "it will throw an error if theres nothing afterit to print. dont worry, extra forgotten spaces don't count.",
      "the arguments for the echo can be anywhere after the first line, that HAS to be \"echo\". the text then will be printed as is."+
        "Note that each space character is regarded as a separated echo command, and will be printed on its own line.",
      "text placed between the doublequotes will transform the characters inside into their respective escape codes. "+
        "\\n will become a new line, \\t wil become a tab, and so on.",
      "text paced between the singlequotes will be taken as literals. \\n will stay \\n, and be printed as such.",
      "if you put a space after a backslas, that space will be taken as a literal, and printed as such."
      ),
    "!!! you can chain these 3 in any way you want to get the exact results you want, and you can use the characters\n"+
      "!!! in isolation, except for the \\ that needs to be doubled.",
    "Finn Willow",
    "FOSS(Temporary)");
  }
}


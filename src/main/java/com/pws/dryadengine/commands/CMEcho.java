package com.pws.dryadengine.commands;

import java.util.Arrays;

import com.pws.dryadengine.func.Debug;
import com.pws.dryadengine.core.App;
import com.pws.dryadengine.core.commands.Command;

public class CMEcho extends Command {
  @Override
  public void construct() {
    this.command = "echo";
    this.options = new String[] {
        "-l", "--log",
        "-h", "--help",
        "-m", "--multiline"
    };
  }

  @Override
  public boolean run(String[] args) {
    loop: for (short i = 0; i < args.length; i++) {
      switch (args[i]) {
        case "-l":
          if (i + 1 != args.length){
            i++;
            Debug.log(args[i]);
            Debug.out("[L]: " + args[i]);
          }
          else
            Debug.print("Nothing to log after -l command at part " + i + ".");
          break;
        case "--log":
          if (i + 1 != args.length) {
            i++;
            Debug.print(args[i]);
            Debug.out("[L]: " + args[i]);
          }
          else
            Debug.print("Nothing to log after --log command at part " + i + ".");
          break;
        case "-m":
          Debug.print("Unimplemented.");
          break;
        case "--multiline":
          Debug.print("Unimplemented.");
          break;
        case "-h":
          this.help.print();
          break loop;
        case "--help":
          this.help.print();
          break loop;
        default:
          Debug.out(args[i]);
          break;
      }

      if (i < args.length - 1)
        Debug.print("\n");
    }
    return true;
  }

  @Override
  public void setHelp() {
    this.help = new CIHelp(
        "echo [--option/s] (String ... arguments)",
        "echo", "-- prints the arguments to the therminal.",
        "echo [option or options to print; can be anywhere] (String or strings of text to be printed)" +
            "!!! dont actually add the parentesis. each part just needs to be separated by a space." +
            "!!! if you want to use spaces in the string, you can eitehr use \"\", '', or \\. these will be explained lower.",
        Arrays.asList(
            "echo",
            "-h or --help",
            "-l or --log",
            "String ... args",
            "\" \"",
            "' '",
            "\\+space"),
        Arrays.asList(
            "prints the strings that are not part of other options.",
            "prints this text.",
            "prints the next string to the end log file. you will never be allowed to completely override the log file. "
                +
                "it will throw an error if theres nothing afterit to print. dont worry, extra forgotten spaces don't count.",
            "the arguments for the echo can be anywhere after the first line, that HAS to be \"echo\". the text then will be printed as is."
                +
                "Note that each space character is regarded as a separated echo command, and will be printed on its own line.",
            "text placed between the doublequotes will transform the characters inside into their respective escape codes. For example "
                +
                "\\n will become a new line, \\t wil become a tab, and so on.",
            "text paced between the singlequotes will be taken as literals. \\n will stay \\n, and be printed as such.",
            "if you put a space after a backslas, that space will be taken as a literal, and printed as such."),
        "!!! you can chain these 3 in any way you want to get the exact results you want, and you can use the characters\n"
            +
            "!!! in isolation, except for the \\ that needs to be doubled.",
        "Finn Willow",
        "FOSS(Temporary)");
  }
}

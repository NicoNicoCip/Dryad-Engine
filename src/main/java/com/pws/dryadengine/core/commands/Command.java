package com.pws.dryadengine.core.commands;

import java.util.*;

import com.pws.dryadengine.commands.CIHelp;
import com.pws.dryadengine.func.Debug;

/**
 * Main abstract class used in the creation of commands.<br> 
 * This is a hookup class so use a ClassHook to find and use<br>
 * these types of classes. Also, make sure you implement the following<br>
 * abstract methids:<br>
 * * setCommand();<br>
 * * setOptions();<br>
 * * setHelp();<br>
 * * construct();<br>
 * * run(String ... args);
 *
 * @author Finn Willow
 * @version 1.0
 * @see {@link CommandManger}
 * @see {@link CMHelp}
 */
public abstract class Command {
  protected String command;
  protected String[] options;
  protected CIHelp help;

  @FunctionalInterface
  protected interface CommandFunction {
      void execute(String... args);
  }
  
  protected Map<String, CommandFunction> optionHandlers = new HashMap<>();
  
  /**
   * Used in the asignation step of the CommandManager class hook,<br>
   * in setting the identifier of the command.<br>
   * <br>
   * Implement:<br>
   * <pre>this.command = "my-command";</pre>
   * Dont use spaces or quotes, not even backslash + space!
   */
  public abstract void setCommand();

  /**
   * Used in the asignation step of the CommandManager class hook,<br>
   * in setting the options for the command.<br>
   * <br>
   * Implement:
   * <pre>
   * this.options = new String[] {
   * // place opions here, idealy like this:
   * "-o1", "--option1",
   * "-o2", "--option2"
   * };
   * </pre>
   */
  public abstract void setOptions();

  /**
   * Used in the asignation step of the CommandManager class hook,<br>
   * in setting the help CommandInterpreter, helpfull in self documentation<br>
   * of the command.<br>
   * <br>
   * Implement:<br>
   * <pre>this.help = new CIHelp(arg/s); </pre>
   * if you want to add your own text use the one argumet version, or<br> 
   * use the multi argument vsrsion for auto generated text.
   * @see {@link CIHelp}
   */
  public abstract void setHelp();

  /**
   * Used in the asignation step of the CommandManager class hook,<br>
   * in setting the options to their respective functions.<br>
   * <br>
   * Implement:
   * <pre>registerOptions((args) -> method(args));</pre>
   * or
   * <pre>registerOptions((args) -> method());</pre> 
   * if your function doest take in arguments from the ran command,<br>
   * for each option.
   */
  public abstract void construct();

  /**
   * Used in the ran command step, after modifying it and saving each runnable<br>
   * argument into one string, as per the rules of spaces; See CommandManager for<br>
   * more info.<br>
   * <br>
   * Implement:
   * <pre>
   * String[] filtered = matching(getOptions(), args);
   * for (String option : filtered) {
   *   executeOption(option, args);
   * }
   * </pre>
   * this is absolutely mandatory to run the methods of the options.<br>
   * since the options cant return anything, you need to use other means<br>
   * to detect if one was ran; See CMEcho for an example implementation.
   *
   * @param args <code>an array of strings representing one full line to be executed,</code><br>
   * <code>like a path, a string or something else.</code>
   */
  public abstract boolean run(String[] args);

  /**
   * Getter for the variable "command"
   * @return String command, as pointer.
   */
  public String getCommand() {
    return command;
  }

  /**
   * Getter for the variable "options"
   * @return String array, as pointer.
   */
  public String[] getOptions() {
    return options;
  }

  /**
   * Getter for the variable "help"
   * @return CIHelp help, as pointer.
   */
  public CIHelp getHelp() {
    return help;
  }

  /**
   * Utility function for getting the matching strings in two arrays,<br>
   * Perfect for getting the options that need running.
   * @param first <code>String[]</code> for the array to be searched.
   * @param second <code>String[]</code> for the array to be searching against.
   * @return <code>String[]</code> of mathing strings, or an empty array if nothing is found.
   */
  protected String[] matching(String[] first, String[] second) {
    Set<String> set1 = new HashSet<>(Arrays.asList(first));
    Set<String> set2 = new HashSet<>(Arrays.asList(second));
    set1.retainAll(set2);
    return set1.toArray(new String[0]);
  }

  /**
   * Utility function for registering a runnable method to one or multiple options.
   * @param handler lambda of the function to be ran.<br> <code> (args) -> theFunction(args) </code> or<br> 
   * <code> (args) -> theFunction() </code> if the function doesn't take any arguments
   * @param options String array (String ... opt) of options to be assigned to.  
   */
  protected void registerOptions(CommandFunction handler, String ... options) {
     Arrays.asList(options).forEach(opt -> optionHandlers.put(opt, handler));
  }

  /**
   * Utility funciton that runs the option's assigned command.
   * @param option the option to be ran.
   * @param args arguents to pass to the option's method.
   */
  protected void executeOption(String option, String... args) {
    CommandFunction handler = optionHandlers.get(option);
    if (handler != null) {
      handler.execute(args);
    } else {
      Debug.println("Option not found: " + option);
    }
  }

  /**
   * Utility function that finds and runs all the options registered in the command
   */
  protected void runOptions(String[] args) {
    String[] filtered = matching(getOptions(), args);
    for (String option : filtered) {
      executeOption(option, args);
    }
  }

  /**
   * Utility funciton for getting the indices of either one of the two searchers in the array.
   */
  protected short getPair(String searcher1, String searcher2, String[] args) {
    short index = getPositionOfString(searcher1, args);
    if(index == -1) index = getPositionOfString(searcher2, args);
    return index;
  }

  /**
   * finds the first ocurence of a string in an array and returns its index as a short.
   */ 
  protected short getPositionOfString(String searcher, String[] args) {
    return (short)Arrays.asList(args).indexOf(searcher);
  }
}

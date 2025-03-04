package com.pws.dryadengine.core.commands;

import java.io.File;
// internal
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

// libraryes
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;

// packaged
import com.pws.dryadengine.core.App;
import com.pws.dryadengine.func.ClassHook;
import com.pws.dryadengine.func.Debug;
import com.pws.dryadengine.func.FileManager;

/**
 * Utility class that when extended from for the first time<br>
 * searches for classes extenging from CMM_Function and adds<br>
 * them to a list in preparation for execution.<br>
 * <br>
 * This class also is in charge with preparing each argument<br>
 * for execution. That includes things like backslashes, single<br>
 * and double quotes, extra unnecesary spaces, and more.
 */
public class CommandManager extends ClassHook<Command> {
  private HashMap<String, Command> commands = new HashMap<>();
  private StringBuffer buffer = new StringBuffer();
  private int cursor = 0;
  private int line = 0;
  private boolean historyView = false;

  public String lineInfo = ">> ";

  private Terminal term;
  private NonBlockingReader reader;
  private File commandHistory;

  private void initHistorial() {
    commandHistory = FileManager.createFile(App.saveFileFolder + "commandHistory.txt");
    FileManager.writeToFile(commandHistory, " ", true);
  }

  public CommandManager() {
    this.load(Command.class, "extends Command");
  }

  @Override
  public void runOn(Command cmmfunc) {
    cmmfunc.setCommand();
    cmmfunc.setOptions();
    cmmfunc.setHelp();
    cmmfunc.construct();
    commands.put(cmmfunc.getCommand(), cmmfunc);
  }

  public Command getOneCommand(String command) {
    return commands.get(command);
  }

  public List<Command> getCommands() {
    return new ArrayList<>(commands.values());
  }

  /**
   * Helper function for running a command trough code<br>
   * <br>
   * TODO: multi-command execution; pipes, exporting, importing, etc.<br>
   * TODO: command functions, which are just functions in command form;<br>
   * youll be able to call for example function() that runs its own commands,<br>
   * takes in arguments and options, etc.
   *
   * @param command - string of the command to run, with its arguments<br>
   *                and options.<br>
   */
  public void runCommand(String command) {
    System.out.println();
    String[] args = breakApart(command);
    FileManager.writeToFile(commandHistory, "\n" + command, true);

    if (commands.containsKey(args[0]))
      commands.get(args[0]).run(Arrays.copyOfRange(args, 0, args.length));
    else
      Debug.println("Command not found.");

    System.out.println();
    buffer.setLength(0);
    cursor = 0;
  }

  // function responsible for detecting the inputs from the keyboard and doing
  // stuff with them, like moving the cursor, deleteing and adding characters,
  // entering and exiting autocomplete mode, and actually running the command.
  private void inputs() throws IOException {
    int input = reader.read(1);
    if (input == -2)
      return;

    // BACKSPACE
    if (input == 8) {
      if (cursor > 0) {
        buffer.deleteCharAt(cursor - 1);
        cursor--;
      }
    }
    // ARROW KEYS
    else if (input == 27) {
      int next1 = reader.read(1);
      if (next1 == 79) {
        int next2 = reader.read(1);
        // UP DOWN RIGHT LEFT
        switch (next2) {
          case 65:
            if (line > 1) {
              line--;
              historyView = true;
            }
            break;

          case 66:
            if (line < FileManager.readLines(commandHistory).length - 1) {
              line++;
              historyView = true;
            } else {
              historyView = false;
              int repeat = FileManager.readLines(commandHistory)[line - 1].length();
              buffer.setLength(0);
              cursor = 0;
            }
            break;
          // RIGHT
          case 67:
            if (cursor < buffer.length())
              cursor++;
            break;

          // LEFT
          case 68:
            if (cursor > 0)
              cursor--;
            break;
        }
      }
    }
    // ENTER
    else if (input == '\r' || input == '\n') {
      line++;
      runCommand(buffer.toString());
    }
    // REST OF CHARACTERS.
    else {
      char c = (char) input;
      buffer.insert(cursor, c);
      cursor++;
      line = FileManager.readLines(commandHistory).length;
      historyView = false;
    }

    // updatign the screen if something actually changed.
    updateWritten();
  }

  // internal utility function used in the process of autocomplete;
  // returns the last whole string between two spaces; can distinguish
  // between literals and quotes.
  private String getWordAtCursor() {
    if (buffer.length() == 0) {
      return "";
    }

    String[] bapStrings = breakApart(buffer.toString().replace("\"", "'"));

    // If cursor is on a space, move it forward until it reaches a character
    int adjustedCursor = cursor;
    while (adjustedCursor < buffer.length() && buffer.charAt(adjustedCursor) == ' ') {
      adjustedCursor++;
    }

    int runningLength = 0;

    for (String word : bapStrings) {
      int wordStart = runningLength;
      int wordEnd = wordStart + word.length();

      if (adjustedCursor >= wordStart && adjustedCursor <= wordEnd) {
        return word;
      }

      runningLength = wordEnd + 1; // +1 for the space separator
    }

    return "";
  }

  private String[] breakApart(String args) {
    args = args.trim().replaceAll("\\s+", " ");
    String c0 = "";
    char c1 = 0;
    char[] characters = args.toCharArray();
    if (characters[characters.length - 1] != ' ') {
      characters = Arrays.copyOf(characters, characters.length + 1);
      characters[characters.length - 1] = ' ';
    }
    int singlequoteStart = -1;
    int doublequoteStart = -1;
    StringBuffer finalString = new StringBuffer();

    for (int i = 0; i < characters.length; i++) {
      // roll the combo for the next loop.
      c0 = String.valueOf(characters[i]);
      if (i + 1 < characters.length)
        c1 = characters[i + 1];
      else
        c1 = 0;

      // create a combo using the two stored chars.
      String combo = c0 + c1;

      switch (c0) {
        case " ":
          if (doublequoteStart == -1 && singlequoteStart == -1)
            finalString.append("\u001F");
          else
            finalString.append(" ");
          break;
        case "\\":
          if (singlequoteStart != -1) {
            finalString.append("\\");
            break;
          }
          if (combo.equals("\\\\"))
            finalString.append("\\");
          else if (combo.equals("\\ "))
            finalString.append(" ");
          else if (combo.equals("\\'"))
            finalString.append("'");
          else if (combo.equals("\""))
            finalString.append("\"");
          else if (combo.equals("\\n"))
            finalString.append("\n");
          else if (combo.equals("\\t"))
            finalString.append("\t");
          i++;
          break;
        // singlequote groups all the text inside as one part, and keeps special
        // characters and spaces.
        case "'":
          if (singlequoteStart != -1) {
            singlequoteStart = -1;
            if (singlequoteStart < doublequoteStart)
              doublequoteStart = -1;
          } else
            singlequoteStart = i;
          break;

        // doublequote groups all the text inside as one part, and keeps the characters
        // inside as literals, by doubling up all the backslashes.
        case "\"":
          if (doublequoteStart != -1) {
            doublequoteStart = -1;
            if (doublequoteStart < singlequoteStart)
              singlequoteStart = -1;
          } else
            doublequoteStart = i;
          break;
        default:
          finalString.append(c0);
          break;
      }
      if (i == characters.length - 1) {
        int smaller = (singlequoteStart < doublequoteStart)
            ? singlequoteStart == -1 ? doublequoteStart : singlequoteStart
            : doublequoteStart == -1 ? singlequoteStart : doublequoteStart;

        if (smaller != -1)
          finalString.append(args.substring(smaller));
      }
    }

    return finalString.toString().split("\u001F");
  }

  // update the line that has prints and updated the line with the string thats
  // being written to currently.
  private void updateWritten() {
    if (historyView) {
      buffer = new StringBuffer(FileManager.readLines(commandHistory)[line]);
      // Set cursor at the end of the buffer
      cursor = buffer.length();
      historyView = false;
    }

    // Print the line info and current buffer.
    String out = "\r" + lineInfo + buffer.toString();
    System.out.print(out);

    // Clear from the current cursor position to the end of the line.
    System.out.print("\033[K");

    // Move the cursor to the correct position:
    // (cursor + lineInfo.length() + 1) calculates the column.
    System.out.print("\033[" + (cursor + lineInfo.length() + 1) + "G");

    System.out.flush();
  }

  public void create() throws Exception {
    term = TerminalBuilder.builder().system(true).jna(true).build();
    reader = term.reader();

    initHistorial();
    while (commandHistory == null && !commandHistory.exists()) {
    }
    line = FileManager.readLines(commandHistory).length;

    updateWritten();
    while (!App.finnishExecution) {
      inputs();
      App.backend.sleep(10);
    }
  }
}

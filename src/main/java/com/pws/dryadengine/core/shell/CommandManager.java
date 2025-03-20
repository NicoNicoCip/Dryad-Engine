package com.pws.dryadengine.core.shell;

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
import com.pws.dryadengine.func.Await;
import com.pws.dryadengine.func.ClassHook;
import com.pws.dryadengine.func.Debug;
import com.pws.dryadengine.func.FileManager;
import com.pws.dryadengine.func.StringUtils;

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
  private HashMap<String, String> aliases = new HashMap<>();
  private HashMap<String, Command> commands = new HashMap<>();
  private StringBuffer buffer = new StringBuffer();
  private int cursor = 0;
  private int line = 0;
  private boolean historyView = false;

  public String lineInfo = ">> ";

  private Terminal term;
  public NonBlockingReader reader;

  private File commandHistory;
  private File bow;
  private void initHistorial() {
    commandHistory = FileManager.createFile(App.systemDataFolder + "/.commandHistory");
    bow = FileManager.createFile(App.systemDataFolder + "/.bow");

    String[] parts = FileManager.readLines(bow, ";");
    for (String func : parts) {
      String alias = "alias" + StringUtils.getBetween(func, "alias", "}") + "}";
      String aName = StringUtils.getBetween(alias, "(", ")").trim();
      String aBody = StringUtils.getBetween(alias, "{", "}");
      // Debug.println("ALIAS: " + alias);
      // Debug.println("ANAME: " + aName);
      // Debug.println("ABODY: " + aBody);
      aliases.put(aName, aBody);
    }
  }

  public CommandManager() {
    this.load(Command.class, "extends Command");
  }

  @Override
  public void runOn(Command cmmfunc) {
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

  public void create() throws Exception {
    term = TerminalBuilder.builder().system(true).jna(true).build();
    reader = term.reader();

    initHistorial();
    Await.signal(commandHistory != null && commandHistory.exists());
    line = FileManager.readLines(commandHistory, "\n\u001F").length;

    updateWritten();
    while (!App.finnishExecution) {
      inputs();
    }
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

    Debug.log(lineInfo + command + "\n");

    if (command.length() <= 0) {
      return;
    }

    String[] args = StringUtils.breakApart(command);
    if (!FileManager.readLines(commandHistory, "\n\u001F")[line - 1].replace("\n\u001F", "").equals(command)) {
      FileManager.updateFile(commandHistory, "\n\u001F" + command, true);
      line++;
    }

    if (aliases.containsKey(args[0])) {
      String[] parts = aliases.get(args[0]).split("\n");
      for(String par : parts) {
        if(par.equals("")) continue;
        String[] com = StringUtils.breakApart(par);
        commands.get(com[0]).run(Arrays.copyOfRange(com, 1, com.length));
      }
    } else if (commands.containsKey(args[0]))
      commands.get(args[0]).run(Arrays.copyOfRange(args, 1, args.length));
    else
      Debug.print("Command not found.");
    
    if(App.finnishExecution) return;

    System.out.println();
    Debug.log("\n");
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

    if (input == 24) { // CTRL+X (multiline toggle)
      App.finnishExecution = true;
    }

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
            if (line < FileManager.readLines(commandHistory, "\n\u001F").length - 1) {
              line++;
              historyView = true;
            } else if (line == FileManager.readLines(commandHistory, "\n\u001F").length - 1) {
              line++;
              historyView = false;
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
      runCommand(buffer.toString());
      line = FileManager.readLines(commandHistory, "\n\u001F").length;
    }
    // REST OF CHARACTERS.
    else {
      char c = (char) input;
      buffer.insert(cursor, c);
      cursor++;
      line = FileManager.readLines(commandHistory, "\n\u001F").length;
      historyView = false;
    }
    // updatign the screen if something actually changed.
    updateWritten();
  } 

  // update the line that has prints and updated the line with the string thats
  // being written to currently.
  private void updateWritten() {
    if (historyView) {
      buffer = new StringBuffer(FileManager.readLines(commandHistory, "\n\u001F")[line]);
      // Set cursor at the end of the buffer
      cursor = buffer.length();
      historyView = false;
    }

    if(App.finnishExecution) return;

    // Print the line info and current buffer.
    System.out.print("\r" + lineInfo + buffer.toString());
    System.out.print("\033[K");
    System.out.print("\033[" + (cursor + lineInfo.length() + 1) + "G");
    System.out.flush();
  }

  public void updateMultilineBuffer(String buffer) {
    int lines = buffer.split("\n").length;

    // Move cursor up by the number of lines in the buffer
    for (int i = 1; i < lines; i++) {
      System.out.print("\033[F"); // Move up one line
    }

    // Clear the current lines by overwriting with spaces
    for (int i = 0; i < lines; i++) {
      System.out.print("\r\033[K"); // Move to start of line and clear it
    }

    // Print the updated buffer in place
    System.out.print(buffer);
  }
}

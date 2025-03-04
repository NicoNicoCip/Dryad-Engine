package test.core;

// internal
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// libraryes
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;

/**
 * Utility class that when extended from for the first time<br>
 * searches for classes extenging from CMM_Function and adds<br>
 * them to a list in preparation for execution.<br>
 * <br>
 * This class also is in charge with preparing each argument<br>
 * for execution. That includes things like backslashes, single<br>
 * and double quotes, extra unnecesary spaces, and more.
 */
public class TestManager extends ClassHook<Test> {
  private HashMap<String,Test> commands = new HashMap<>();
  private StringBuffer buffer = new StringBuffer();
  private int cursor = 0;

  public String lineInfo = "[ Test ] >> ";

  Terminal term;
  NonBlockingReader reader;

  public TestManager() {
    this.load(Test.class, "extends Test");
  }

  @Override 
  public void runOn(Test cmmfunc){
    cmmfunc.construct();
    commands.put(cmmfunc.test, cmmfunc);
  }

  public Test getOneCommand(String command) {
    return commands.get(command);
  }

  public List<Test> getCommands() {
    return new ArrayList<>(commands.values());
  }

  // Im sorry, but its a lot to explain, and i dont want to fill the javadoc with too much
  // garbage.
  //
  // This function takes in a unmodified string, from where all the spaces that dont have
  // a \ before them are stripped. then, another algorithm searches for double and single
  // quotes and assigns splits the words prperly. here are the rules:
  //
  // 1. Text within " " will use escape codes. as in \n will become a new line \t a tab, etc.
  // 2. Text within ' ' will be treated as a literal and will not be modified. 
  // 3. Text with "\ + space" will treat the space as a literal.
  // 4. By default, all text is treated as if it were between doublequotes.
  // 5. if a single quote or doublequote is found, it will be treated as a character.
  // 6. building on rule 5, you cant expect text of the type: 'here we are. "\n dude' wake up"
  // to work. Why, because internaly the order is Singlequote-open, Doublequote-open,
  // Singequote-open => previous double quote is a character, Doublequote-open.
  // 7. you can youse a shorthand, if necesary, by typing just one of the quote simbols, and all
  // the characters after that will use its rule. if you dont want it to be registered as one,
  // use \ before it to make it into a literal.
  //
  // Im not done. after the string is proeprly broken into its substrings, the first one that will
  // allways be a command, and the rest its options and arguments, again, with no usless or separating
  // spaces. after that, it FINALLY gets the command and runs it, without the first, which, again,
  // is allways the command. if you dont want pain, make sure that whatever command you're making
  // does NOT have spaces, check spelling and make sure lowercase and uppercase letters are proper.

  /**
   * Helper function for running a command trough code<br>
   * <br>
   * TODO: multi-command execution; pipes, exporting, importing, etc.<br>
   * TODO: command functions, which are just functions in command form;<br>
   * youll be able to call for example function() that runs its own commands,<br>
   * takes in arguments and options, etc.
   *
   * @param command - string of the command to run, with its arguments<br>
   * and options.<br>
   */
  public void runCommand(String command, String arguments) {
    System.out.println();
    if(commands.get(command) == null)
      Debug.print("Test not found.");
    else
      commands.get(command).run(arguments);
    buffer.setLength(0);
    cursor = 0;
  }

  // function responsible for detecting the inputs from the keyboard and doing
  // stuff with them, like moving the cursor, deleteing and adding characters,
  // entering and exiting autocomplete mode, and actually running the command.
  private void inputs() throws IOException {
    int input = reader.read(1);
    if(input == -2) return;

    // BACKSPACE
    if(input == 8) {
      if(cursor > 0) {
        buffer.deleteCharAt(cursor - 1);
        cursor--;
      }
    } 
    // ARROW KEYS
    else if(input == 27) {
      int next1 = reader.read(1);
      if(next1 == 79) {
        int next2 = reader.read(1);
        // UP DOWN RIGHT LEFT
        switch(next2) {
          // 65 and 66 are for up and down.
          // TODO: histry.
          
          // RIGHT
          case 67:
            if(cursor < buffer.length()) cursor++;
            break;
          
          // LEFT
          case 68:
            if(cursor > 0) cursor--;
            break;
        }
      }
    } 
    // ENTER
    else if(input == '\r' || input == '\n') {
      String[] toRun = buffer.toString().split(" -a ");
      if(toRun.length == 1)
        runCommand(toRun[0], "");
      else
        runCommand(toRun[0], toRun[1]);
    } 
    // REST OF CHARACTERS.
    else {
      char c = (char) input;
      buffer.insert(cursor, c);
      cursor++;
    }

    // updatign the screen if something actually changed.
    updateWritten();
  }

  // internal utility function used in the process of autocomplete;
  // returns the last whole string between two spaces; can distinguish
  // between literals and quotes.
  //
  // TODO: quotation marks.
  private String getWordAtCursor() {
    if(buffer.length() == 0) {
      return "";
    }

    StringBuilder prev = new StringBuilder();
    StringBuilder next = new StringBuilder();

    // from the before the cursor start going backwards untill reach the start,
    // or a space that DOESN'T have a backslash behind it.
    for (int i = buffer.substring(0, cursor).length() -1; i >= 0; i--) {
      char current = buffer.charAt(i);

      if(i > 0 && current == ' ' && buffer.charAt(i-1) != '\\')
        break;

      prev.append(current);
    }

    //from the cursor start going forwards untill reached the end, or a space
    //that DOESN'T have a backspash before it; if the cursor is not on a space.
    if(cursor < buffer.length() && buffer.charAt(cursor) != ' '){
      for(int i = cursor; i < buffer.length(); i++) {
        char current = buffer.charAt(i);

        if(current == ' ' && i > 0 && buffer.charAt(i-1) != '\\')
          break;

        next.append(current);
      }
    }

    return prev.reverse().toString() + next.toString();
  }

  // update the line that has prints and updated the line with the string thats
  // being written to currently.
  private void updateWritten() {
    String out = "\r" + lineInfo + buffer.toString() + " ";
    System.out.print(out + "\033[" + (cursor + lineInfo.length() + 1) + "G");    
    System.out.flush();
  }

  public void create() throws Exception {
    term = TerminalBuilder.builder().system(true).jna(true).build();
    reader = term.reader();

    updateWritten();
    while (true) {
      inputs();
    }
  }
}

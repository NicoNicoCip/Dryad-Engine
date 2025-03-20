package com.pws.dryadengine.commands;

import java.io.File;
import java.util.Arrays;

import com.pws.dryadengine.core.App;
import com.pws.dryadengine.core.shell.Command;
import com.pws.dryadengine.func.Debug;
import com.pws.dryadengine.func.FileManager;

public class CMFile extends Command {
  @Override
  public void construct() {
    this.command = "file";
    this.options = new String[] {
        "-c", "--create",
        "-d", "--delete",
        "-j", "--jab",
        "-i", "--inspect",
        "-m", "--metadata",
        "-p", "--permisions",
        "-h", "--help"
    };
    registerOptions((args) -> fileCreate(args), "-c", "--create");
    registerOptions((args) -> fileDelete(args), "-d", "--delete");
    registerOptions((args) -> fileJab(args), "-j", "--jab");
    registerOptions((args) -> fileInspect(args), "-i", "--inspect");
    registerOptions((args) -> fileMeta(args), "-m", "--metadata");
    registerOptions((args) -> filePermisions(args), "-p", "--permisions");
    registerOptions((args) -> fileHelp(), "-h", "--help");
  }

  private boolean complete = false;

  @Override
  public boolean run(String[] args) {
    String[] filtered = matching(getOptions(), args);
    for (String option : filtered) {
      executeOption(option, args);
    }

    if (!complete)
      Debug.print("No option provided.");
    return true;
  }

  public void fileCreate(String[] args) {
    if (FileManager.createFile(args[1]).exists())
      Debug.print("File " + args[1] + " created succesfully");
    else
      Debug.print("Could not create " + args[1]);
    complete = true;
  }

  public void fileDelete(String[] args) {
    try {
      // if there is no file to delete, or the file doesnt exist, break and log.
      if (!FileManager.fileExists(args[1])) {
        Debug.print("Could not find the file to delete.");
        complete = true;
        return;
      }

      // Ask the user if they want to delete the file, and show off options.
      Debug.print("Are you sure you want to delete \"" + args[1] + "\" ? [Y/n]");
      while (true) {
        int input = App.commandMan.reader.read(1);
        if (input != -2) {
          char c = (char) input;
          if (c == 'y' || c == 'Y') {
            // delete the file if the right option is chosen
            if (FileManager.deleteFile(args[1])) {
              Debug.print("\nDeleted \"" + args[1] + "\" succesfully");
              break;
            } else {
              Debug.print("\nCould no delete \"" + args[1] + "\"");
              break;
            }

            // cancel if they pressed n
          } else if (c == 'n' || c == 'N') {
            Debug.print("\nCanceled deletion.");
            break;

            // break the loop if ctrl x is pressed
          } else if (input == 24) {
            Debug.print("\n^X");
            break;

            // for anything else redisplay the options
          } else {
            Debug.println("\nThe only options are y or n, uppercase or lowercase.");
          }
        }
      }

      complete = true;
    } catch (Exception e) {
      Debug.logError(e);
    }

  }

  public void fileJab(String[] args) {
    File file = FileManager.getFile(args[1]);
    if (file.exists()) {
      String permisions = "123";
      if (file.canRead())    permisions = permisions.replace("1", "r");
      if (file.canWrite())   permisions = permisions.replace("2", "w");
      if (file.canExecute()) permisions = permisions.replace("3", "e");
      permisions = permisions.replace("1", "-").replace("2", "-").replace("3", "-");
      Debug.print("File " + args[1] + " (with permisions for " + permisions + ") exists.");
    } else
      Debug.print("File " + args[1] + " does not exist.");

    complete = true;
  }

  public void fileInspect(String[] args) {
    if (FileManager.fileExists(args[1]))
      Debug.print(FileManager.readFile(args[1]));
    else
      Debug.print("Error occured in inspecting the file " + args[1]);
    complete = true;
  }

  public void fileMeta(String[] args) {
    if (FileManager.fileExists(args[1]))
      Debug.print(FileManager.extractMetadata(FileManager.getFile(args[1])));
    else
      Debug.print("Could not find the metadata of the file " + args[1]);
    complete = true;
  }

  public void filePermisions(String[] args) {
    complete = true;
    boolean stringMode = false;
    Debug.println("[" + args[1] + "]");

    // make sure that arg1 is either a number between 0 and 7,or a 3 letter string following the base rwe format. 
    if (args[1].length() != 1 && Integer.valueOf(args[1]) < 0 && Integer.valueOf(args[1]) > 7) {
      Debug.print("Permisions malformed. see \"file -h\" for more info.");
      return;
    }

    if (!FileManager.fileExists(args[2])) {
      Debug.print("File " + args[2] + " does not exist.");
      return;
    }

    File f = FileManager.getFile(args[2]);
    int perm = Integer.valueOf(args[1]);
    f.setReadable(getBit(perm, 0) == 1);
    f.setWritable(getBit(perm, 1) == 1);
    f.setExecutable(getBit(perm, 2) == 1);
    Debug.print("File " + args[2] + " changed its permisions succesfully.");
  }

  int getBit(int n, int b) {
    return (n >> b) & 1;
  }

  public void fileHelp() {
    this.help.print();
  }

  @Override
  public void setHelp() {
    this.help = new CIHelp(
        "file (option/s) (file)",
        "file", "-- used in managing one or multiple files.",
        "file (option to perform on a file) (the file to perform the operation on) !!! except for a fuew options, this command only takes only two argumemts,"
            +
            " in the specified order. !!!",
        Arrays.asList(
            "file",
            "-c or --create",
            "-d or --delete",
            "-j or --jab",
            "-i or --inspect",
            "-m or --metadata",
            "-p or --permisions",
            "-h or --help"),
        Arrays.asList(
            "the command to be run. it takes in a option, and a file path in relative or absolute. you can set the local path using the \"path\" "+
                "command (help path). Also, se the \"dir\" command (help dir) for managing directories.",
            "used in creating a file at the specified path. to set its name and extension just put after / at the end of the path.",
            "deletes a file at the path. needs comfirmation to complete.",
            "it pokes at the file to see if its allive and usabe (not corrupted)",
            "insoects the contents of the file, and prints it to the terminal.",
            "prints the metadata of the current file. creation date, author, among other things.",
            "modifies the permisions for this file. it takes two arguments, the permision level and the file. for the permision level, you have to write one" +
                "number between 0 and 7, which in binary represent the permisions for reading, writing, and executing the file, with 0 representing permision denied" +
                "and 1 representing permision granted. so for example, the number 5, in binary is 101, and means that the file can be read, can't be written to, and can" +
                "be executed.",
            "prints this text."),
        "this command is normally used just for creating and managing files on a basic level. if you want to modify the contents of the files you need "+
            "permisions, by using the \"mod\" command (help mod), and to use either a text editor like the built in \"edit\" command (help edit), or by using pipes. " +
            "options for this command are not ... optional, unlike most other commands, since it can perform most file operations.",
        "Finn Willow",
        "FOSS (Temporary)");
  }
}

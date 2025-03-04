package com.pws.dryadengine.commands;

import java.util.Arrays;

import com.pws.dryadengine.core.commands.Command;
import com.pws.dryadengine.func.Debug;
import com.pws.dryadengine.func.FileManager;

public class CMFile extends Command {
  @Override
  public void setCommand() {
    this.command = "file";
  }

  @Override
  public void setOptions() {
    this.options = new String[] {
      "-c", "--create",
      "-d", "--delete",
      "-t", "--test",
      "-j", "--jab",
      "-m", "--metadata",
      "-h", "--help"
    };
  }

  @Override
  public void construct() {
    registerOptions((args) -> fileCreate(args), "-c", "--create");
    registerOptions((args) -> fileDelete(args), "-d", "--delete");
    registerOptions((args) -> fileTest(args), "-t", "--test");
    registerOptions((args) -> fileJab(args), "-j", "--jab");
    registerOptions((args) -> fileMeta(args), "-m", "--metadata");
    registerOptions((args) -> fileHelp(), "-h", "--help");
  }

  private boolean complete = false;

  @Override
  public boolean run(String[] args) {
    String[] filtered = matching(getOptions(), args);
    for (String option : filtered) {
      executeOption(option, args);
    }
    if(!complete) Debug.print("No option provided.");
    return complete;  
  }

  public void fileCreate(String[] args) {
    

    complete = true;
  }

  public void fileDelete(String[] args) {
    
  }

  public void fileTest(String[] args) {

  }

  public void fileJab(String[] args) {

  }

  public void fileMeta(String[] args) {
    
  }

  public void fileHelp() {
    this.help.print();
  }

  @Override
  public void setHelp() {
    this.help = new CIHelp(
        "file (option/s) (file)", 
        "file", "-- used in managing one or multiple files.", 
        "file (option to perform on a file) (the file to perform the operation on) [optional data for more operations]", 
        Arrays.asList(
          "file",
          "-c or --create", 
          "-d or --delete",
          "-j or --jab",
          "-i or --inspect",
          "-m or --metadata",
          "-h or --help"
          ), 
        Arrays.asList(
          "the command to be run. it takes in a option, and a file path in absolute. the path can also be relative to your project by using" +
            "the \"~/\" identifier. you can set the local path using the \"path\" command (help path). Also, se the \"dir\" command (help dir) for managing" +
            "directories.",
          "used in creating a file at the specified path. to set its name and extension just put after / at the end of the path.",
          "deletes a file at the path. needs comfirmation to complete.",
          "it pokes at the file to see if its allive and usabe (not corrupted)",
          "insoects the contents of the file, and prints it to the terminal.",
          "prints the metadata of the current file. creation date, author, among other things.",
          "prints this text."
          ), 
        "this command is normally used just for creating and managing files on a basic level. if you want to modify the contents of the files you need "+
          "permisions, by using the \"mod\" command (help mod), and to use either a text editor like the built in \"edit\" command (help edit), or by using pipes."+
          "options for this command are not ... optional, unlike most other commands, since it can perform most file operations.", 
        "Finn Willow", 
        "FOSS (Temporary)");
  }
}

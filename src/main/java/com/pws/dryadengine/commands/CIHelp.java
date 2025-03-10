package com.pws.dryadengine.commands;

import java.util.List;
import com.pws.dryadengine.func.Debug;

public class CIHelp {
  // At the start of your class or file
  private static final String TOP_BOX = "\u250C\u2500";    // ┌─
  private static final String MID_BOX = "\u2502";          // │
  private static final String BOT_BOX = "\u2514\u2500";    // └─
  private String helpText = "";

  public CIHelp(String text) {
    helpText = text;
  }

  /* intro is just the command and nothing more
   * 
   * under name is the brief description of the command
   *
   * under synopsis you get a brief explaination on the composing parts of the command
   *
   * under description you get brief descriptions on each part of the command and its
   *
   * then author of the command
   *
   * a page to report bugs
   *
   * copyright 
   *    Copyright © 2023 Free Software Foundation, Inc.  License GPLv3+: GNU GPL version 3 or later <https://gnu.org/licenses/gpl.html>.
   *    This is free software: you are free to change and redistribute it.  There is NO WARRANTY, to the extent permitted by law.
   *
   * theres also a "see also".
   */

  public CIHelp(String intro, String name, String name_info, String synopsis, List<String> desc_elements, 
    List<String> desc_info, String desc_footer, String author, String license) {
    StringBuilder buffer = new StringBuilder();
    buffer.append("[START - " + name + "]\n");
    buffer.append(intro + "\n\n");

    buffer.append("[NAME]\n");
    buffer.append("  " + name + " " + name_info + "\n\n");
    
    buffer.append("[DESCRIPTION]\n");
    buffer.append("  " + synopsis + "\n\n");
    
    for(int i = 0; i < desc_elements.size(); i++) {
      buffer.append(formatArrowText("  " + desc_elements.get(i) + " > " + desc_info.get(i), 120));
      buffer.append("\n");
    }
    buffer.append("  " + desc_footer + "\n\n");

    buffer.append("[AUTHOR]\n");
    buffer.append("  " + author + "\n\n");

    buffer.append("[LICENSE]\n");
    buffer.append("  " + license + "\n\n");
    buffer.append("[END]");

    helpText = formatText(buffer.toString(), 150).replaceAll("#", " ");
  }

  public void print() {
      Debug.print(helpText);
  }

  public String getHelpText() {
      return helpText;
  }


  private static String formatText(String input, int maxLength) {
    StringBuilder result = new StringBuilder();
    List<String> lines = List.of(input.split("\n"));
    
    for (String line : lines) {
      if(lines.indexOf(line) != 0 && lines.indexOf(line) != lines.size() -1) result.append(".  ");
      
      if (line.trim().isEmpty()) {
        result.append("\n");
        continue;
      }
      
      String[] words = line.trim().split(" ");
      int currentLineLength = 3; // Starting after ".  "
      
      for (String word : words) {
        if (currentLineLength == 3) {
          result.append(word);
          currentLineLength += word.length();
          continue;
        }
        
        if (currentLineLength + word.length() + 1 > maxLength) {
          result.append("\n.  ");
          currentLineLength = 3;
        }
        
        result.append(" ").append(word);
        currentLineLength += word.length() + 1;
      }
      if(lines.indexOf(line) != lines.size() -1)
      result.append("\n");
    }
    
    return result.toString();
  }

  private static String formatArrowText(String input, int maxLength) {
      String[] parts = input.split(">");
      if (parts.length != 2) return input;
      
      String element = parts[0].trim();
      String description = parts[1].trim();
      String[] descWords = description.split(" ");
      
      StringBuilder result = new StringBuilder();
      StringBuilder tempResult = new StringBuilder();
      int currentLineLength = 0;
      int lineCount = 1;
      
      // First line
      result.append(element).append(" > ");
      currentLineLength = element.length() + 4;
      
      // Calculate indent for alignment
      String indent = "#".repeat(element.length());
      
      for (String word : descWords) {
          if (currentLineLength + word.length() + 1 > maxLength) {
              tempResult.append("\n").append(indent).append(" | ");
              currentLineLength = indent.length() + 3;
              lineCount++;
          }
          if (lineCount == 1) {
              result.append(word).append(" ");
          } else {
              tempResult.append(word).append(" ");
          }
          currentLineLength += word.length() + 1;
      }
      
      // Convert the result based on line count
      String finalResult = result.toString();
      if (lineCount == 1) {
          return finalResult;
      }
      
      // Replace the arrows with box characters
      String[] lines = (finalResult + tempResult.toString()).split("\n");
      StringBuilder boxedResult = new StringBuilder();
      
      boxedResult.append(lines[0].replace(">", ">" + TOP_BOX)).append("\n");
      
      for (byte i = 1; i < lines.length - 1; i++) {
          boxedResult.append(lines[i].replace("|", " " + MID_BOX + " ")).append("\n");
      }
      
      boxedResult.append(lines[lines.length - 1].replace("|", " " + BOT_BOX));
      
      return boxedResult.toString();
  }
}

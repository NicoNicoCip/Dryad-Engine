package com.pws.dryadengine.func;

import java.util.Arrays;

public abstract class StringUtils {

  public static String getBetween(String text, String start, String end) {
    int startIndex = text.indexOf(start);
    if (startIndex == -1)
      return ""; // Start string not found

    startIndex += start.length();
    int endIndex = text.indexOf(end, startIndex);
    if (endIndex == -1)
      return ""; // End string not found

return text.substring(startIndex, endIndex);
  }

  // internal utility function used in the process of autocomplete;
  // returns the last whole string between two spaces; can distinguish
  // between literals and quotes.
  public static String getWordAtCursor(String buffer, int pos) {
    if (buffer.length() == 0) {
      return "";
    }

    String[] bapStrings = breakApart(buffer.replace("\"", "'"));

    // If cursor is on a space, move it forward until it reaches a character
    int adjustedCursor = pos;
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

  public static String[] breakApart(String args) {
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
}

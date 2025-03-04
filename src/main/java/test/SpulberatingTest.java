package test;

import java.lang.reflect.Array;
import java.util.Arrays;

import test.core.Debug;
import test.core.Test;

public class SpulberatingTest extends Test {
  @Override
  public void construct() {
    this.test = "sbt";
  }

  @Override
  public boolean run(String args) {
    args = args.trim().replaceAll("\\s+", " ");
    String c0 = "";
    char c1 = 0;
    char[] characters = args.toCharArray();
    if(characters[characters.length -1] != ' ') {
      characters = Arrays.copyOf(characters, characters.length +1);
      characters[characters.length-1] = ' ';
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
      Debug.superPrint(combo);
      switch (c0) {
        case " ":
          if (doublequoteStart == -1 && singlequoteStart == -1)
            finalString.append("\u001F");
          else
            finalString.append(" ");
          break;
        case "\\":
          if (singlequoteStart != -1){
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

      Debug.superPrint(singlequoteStart);
      Debug.superPrint(doublequoteStart);

      if (i == characters.length - 1) {
        int smaller = (singlequoteStart < doublequoteStart)
            ? singlequoteStart == -1 ? doublequoteStart : singlequoteStart
            : doublequoteStart == -1 ? singlequoteStart : doublequoteStart;

        if (smaller != -1)
          finalString.append(args.substring(smaller));
      }

      Debug.superPrint(Arrays.toString(finalString.toString().split("\u001F")));
      Debug.superEnd();
    }

    return false;
  }
}

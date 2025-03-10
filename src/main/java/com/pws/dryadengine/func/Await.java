package com.pws.dryadengine.func;

public class Await {
  /**
   * Pauses execution untill the condition is true.
   */
  public static void signal(boolean condition) {
    //stagger execution unill condition is true
    while (!condition) {}
  }

  /**
   * Pauses execution for the specified number of rames.<br>
   * Basically, budget Thread.sleep().
   */
  public static void frames(int count) {
    while (count > 0) {
      count--;
    }
  }

  /**
   * It returns true each <code>duration</code> frames.
   * @param duration the duration in frames between 0 and n.
   * @param counter pointer that stores the frame between 0 and <code>duration.</code>
   * @return true if the counter is equal to the duration.
   */
  public static boolean period(int duration, int counter) {
    if(duration < 0) {
      Debug.logError(new Exception("Duration can't be negative."));
      return false;
    }

    if(counter != duration) {
      counter++;
      return false;
    } else {
      counter = 0;
      return true;
    }
  }

  /**
   * It returns true each <code>duration</code> frames.
   * @param duration the duration in frames between 0 and n.
   * @param counter pointer that stores the frame between 0 and <code>duration.</code>
   * @param ascending declares weather the <code>counter</code> increases untill it reaches<br>
   * the <code>duration</code> or if it starts at it and decreases untill it reaches 0.
   * @return true if ascending and <code>counter</code> equals <code>duration</code>,<br>
   * or if descending and <code>counter</code> equals <code>0</code>
   */
  public static boolean period(int duration, int counter, boolean ascending) {
    if(duration < 0) {
      Debug.logError(new Exception("Duration can't be negative."));
      return false;
    }

    if(ascending && counter != duration) {
      counter++;
    } else if(ascending) {
      counter = 0;
      return true;
    } else if(!ascending && counter != 0) {
      counter--;
    } else if(!ascending) {
      counter = duration;
      return true;
    }

    return false;
  }
}

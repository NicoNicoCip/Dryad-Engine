package test.core;

import java.util.*;

public abstract class Test {
  public String test="";
  public abstract void construct();
  public abstract boolean run(String args);

  protected String[] matching(String[] first, String[] second) {
    Set<String> set1 = new HashSet<>(Arrays.asList(first));
    Set<String> set2 = new HashSet<>(Arrays.asList(second));
    set1.retainAll(set2);
    return set1.toArray(new String[0]);
  }

  protected short getPair(String searcher1, String searcher2, String[] args) {
    short index = getPositionOfString(searcher1, args);
    if(index == -1) index = getPositionOfString(searcher2, args);
    return index;
  }

  protected short getPositionOfString(String searcher, String[] args) {
    return (short)Arrays.asList(args).indexOf(searcher);
  }
}

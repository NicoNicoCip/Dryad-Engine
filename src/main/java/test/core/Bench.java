package test.core;

public class Bench {
  public static final TestManager commandMan = new TestManager();
  public static final String executionFolder = "src/main/java/"; 
  
  public static void main(String[] args) {
    Debug.print("Test bench active.");
    try {
      commandMan.create();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

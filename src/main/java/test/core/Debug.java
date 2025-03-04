package test.core;

public class Debug {
  private static StringBuffer superPrintBuffer = new StringBuffer();
  
  public static <T> void print(T args) {
    System.out.println(args.toString());
  }

  public static <T> void superPrint(T args) {
    superPrintBuffer.append("[" + args + "] ");
  }

  public static <T> void superEnd() {
    if(!superPrintBuffer.isEmpty()) {
      Debug.print(superPrintBuffer.toString());
      superPrintBuffer.delete(0, superPrintBuffer.length());
    }
  }
}

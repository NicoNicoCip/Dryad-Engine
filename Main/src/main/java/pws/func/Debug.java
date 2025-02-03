package main.java.pws.func;

public abstract class Debug {
    public static final <T> void print(T toPrint) {
        LogsManager.log(toPrint.toString());
        System.out.print(toPrint.toString());
    }

    public static final <T> void println(T toPrint) {
        LogsManager.log(toPrint.toString());
        System.out.println(toPrint);
    }
}

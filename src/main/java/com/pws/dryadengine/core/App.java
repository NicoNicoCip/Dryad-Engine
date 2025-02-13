package main.java.com.pws.dryadengine.core;

import main.java.com.pws.dryadengine.func.LogsManager;

public class App {
    @SuppressWarnings("unused")
    private static final String version = "0.1.5";

    public static volatile boolean finnishExecution = false;
    public static final String saveFileFolder = "data/saves/";
    public static final Thread backend = new Thread(new BackendEnv());
    public static final Thread frontend = new Thread(new FrontendEnv());
    
    public static void main(String[] args) {
        try {
            backend.start();
            frontend.start();
            while (true) {
                if(finnishExecution){
                    System.exit(0);
                    backend.join();
                    frontend.join();
                    break;
                }
            }
        } catch (Exception e) {
            LogsManager.logErrors(e);
        }
    }
}

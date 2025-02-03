package main.java.pws.core;

import main.java.pws.func.LogsManager;
public class App {
    public static volatile boolean finnishExecution = false;
    public static final String saveFileFolder = "/Main/data/saves/";

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

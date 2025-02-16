package main.java.com.pws.dryadengine.core;

import main.java.com.pws.dryadengine.core.scripts.Manager;

public class FrontendEnv implements Runnable {

    @Override
    public void run() {
        try {
            Manager.create();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
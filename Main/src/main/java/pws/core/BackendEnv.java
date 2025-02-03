package main.java.pws.core;

import java.util.Scanner;

import main.java.pws.func.CommandPallete;
import main.java.pws.func.Debug;
import main.java.pws.func.LogsManager;

public class BackendEnv implements Runnable {

    @Override
    public void run() {
        try {
            Scanner scan = new Scanner(System.in);
            
            LogsManager.createLogFile();
            Debug.println("Started Aplication.");
            Debug.println("Write 'help' for a list of all the commands.");
    
            while (!App.finnishExecution) {
                Debug.println("----------------");
                Debug.print("\n>> ");
    
                String scanOut = scan.nextLine();
                LogsManager.log(scanOut);
    
                if(!CommandPallete.runCommand(scanOut)){
                    App.finnishExecution = true;
                    break;
                }
            }
    
            scan.close();
        } catch (Exception e) {
            LogsManager.logErrors(e);
        }

    }
    
}

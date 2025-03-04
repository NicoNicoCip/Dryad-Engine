package com.pws.dryadengine.core;

// packaged
import com.pws.dryadengine.core.commands.CommandManager;
import com.pws.dryadengine.core.scripts.ScriptManager;
import com.pws.dryadengine.func.Debug;
import com.pws.dryadengine.types.Node;

public class App {
  public static volatile boolean finnishExecution = false;

  public static final String saveFileFolder = "data/saves/";
  public static final String executionFolder = "src/main/java";
  public static final Thread backend = new Thread(new BackendEnv());
  public static final Thread frontend = new Thread(new FrontendEnv());

  public static final ScriptManager scriptMan = new ScriptManager();
  public static final CommandManager commandMan = new CommandManager();
  public static final Node root = new Node();

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
      Debug.logError(e);
    }
  }
}

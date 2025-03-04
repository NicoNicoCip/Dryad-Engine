package com.pws.dryadengine.core;

import com.pws.dryadengine.func.Debug;

public class BackendEnv implements Runnable {

  @Override
  public void run() {
    try {
      Debug.println("Begin execution: ");
      App.commandMan.create();
    } catch (Exception e) {
      Debug.logError(e);
    }
  }
}

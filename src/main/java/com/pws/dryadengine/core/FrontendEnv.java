package com.pws.dryadengine.core;

import com.pws.dryadengine.func.Debug;

public class FrontendEnv implements Runnable {
  private static byte run = 0;

  @Override
  public void run() {
    if(run == 1) {
      try {
        App.scriptMan.create();
      } catch (Exception e) {
        Debug.logError(e);
      }
    }
  }
}

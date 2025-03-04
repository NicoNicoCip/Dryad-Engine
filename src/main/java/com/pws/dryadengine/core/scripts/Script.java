package com.pws.dryadengine.core.scripts;

public abstract class Script {
  public String tag = null;
  public int pc;
  public abstract void plant();
  public abstract void grow();

  public void setPC() {
    this.pc = 0;
  }

  public int getPc() {
    return pc;
  }
}

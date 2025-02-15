package main.java.com.pws.dryadengine.core.scripts;

public abstract class Script extends ScriptManager{
    public String tag = null;
    public int pc = -1;
    public abstract void plant();
    public abstract void grow();
}
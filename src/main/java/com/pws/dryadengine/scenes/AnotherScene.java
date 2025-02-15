package main.java.com.pws.dryadengine.scenes;

import main.java.com.pws.dryadengine.core.scripts.Script;
import main.java.com.pws.dryadengine.func.Debug;

public class AnotherScene extends Script{

    @Override
    public void plant() {
        Debug.println("This is scene 0.");
        replant(1);
    }

    @Override
    public void grow() {
        
    }
}

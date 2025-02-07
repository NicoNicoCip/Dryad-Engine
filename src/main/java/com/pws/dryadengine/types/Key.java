package main.java.com.pws.dryadengine.types;

public class Key {
    public int code;
    public State state;
    
    public static enum State {
        pressed,
        held,
        released
    }

    public Key() {
    }

    public Key(int code, State state) {
        this.code = code;
        this.state = state;
    }
    
}

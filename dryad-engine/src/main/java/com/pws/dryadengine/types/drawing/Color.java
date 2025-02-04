package main.java.com.pws.dryadengine.types.drawing;

public class Color {
    private byte r;
    private byte g;
    private byte b;

    public byte getR() {
        return r;
    }

    public byte getG() {
        return g;
    }
    
    public byte getB() {
        return b;
    }

    public void setR(byte r) {
        this.r = r;
    }

    public void setG(byte g) {
        this.g = g;
    }

    public void setB(byte b) {
        this.b = b;
    }

    public static Color getHSBColor(float hue, float saturation, float brightness) {
        Color color = new Color();
        
        // Constrain input values
        hue = hue % 1.0f;
        saturation = Math.max(0, Math.min(1, saturation));
        brightness = Math.max(0, Math.min(1, brightness));
        
        float c = brightness * saturation;
        float x = c * (1 - Math.abs((hue * 6) % 2 - 1));
        float m = brightness - c;
        
        float r, g, b;
        if (hue < 1.0f/6.0f) {
            r = c; g = x; b = 0;
        } else if (hue < 2.0f/6.0f) {
            r = x; g = c; b = 0;
        } else if (hue < 3.0f/6.0f) {
            r = 0; g = c; b = x;
        } else if (hue < 4.0f/6.0f) {
            r = 0; g = x; b = c;
        } else if (hue < 5.0f/6.0f) {
            r = x; g = 0; b = c;
        } else {
            r = c; g = 0; b = x;
        }
        
        color.setR((byte)Math.round((r + m) * 255));
        color.setG((byte)Math.round((g + m) * 255));
        color.setB((byte)Math.round((b + m) * 255));
        
        return color;
    }
}

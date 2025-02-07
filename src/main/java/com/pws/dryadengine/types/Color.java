package main.java.com.pws.dryadengine.types;

public class Color {
    public byte r;
    public byte g;
    public byte b;
    public byte a;

    public Color(){

    }

    public Color(byte r, byte g, byte b, byte a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public Color(int r, int g, int b, int a) {
        this((byte)r, (byte)g, (byte)b, (byte)a);
    }

    public static Color getHSBColor(float hue, float saturation, float brightness, byte alpha) {
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
        
        color.r = (byte)Math.round((r + m) * 255);
        color.g = (byte)Math.round((g + m) * 255);
        color.b = (byte)Math.round((b + m) * 255);
        color.a = alpha;
        
        return color;
    }
}

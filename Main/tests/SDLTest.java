import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import com.sun.jna.Library;
import com.sun.jna.Native;

public class SDLTest {
    // Load the SDL2 library using JNA
    public interface SDL2 extends Library {
        SDL2 INSTANCE = Native.load("SDL2", SDL2.class);
        int SDL_Init(int flags);
        void SDL_Quit();
    }

    @Test
    void testSDLInit() {
        int result = SDL2.INSTANCE.SDL_Init(0);
        assertEquals(0, result, "SDL_Init should return 0 on success");
        SDL2.INSTANCE.SDL_Quit(); // Clean up SDL
    }
}

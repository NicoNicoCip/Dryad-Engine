import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public class SDLWindowTest {
    public interface SDL2 extends Library {
        SDL2 INSTANCE = Native.load("SDL2", SDL2.class);
        int SDL_Init(int flags);
        Pointer SDL_CreateWindow(String title, int x, int y, int w, int h, int flags);
        void SDL_DestroyWindow(Pointer window);
        void SDL_Quit();
        void SDL_Delay(int ms);
    }

    @Test
    void testCreateWindow() {
        int result = SDL2.INSTANCE.SDL_Init(0x00000020); // SDL_INIT_VIDEO
        assertEquals(0, result, "SDL_Init should return 0 on success");

        Pointer window = SDL2.INSTANCE.SDL_CreateWindow("Test Window", 100, 100, 640, 480, 0);
        assertNotNull(window, "SDL_CreateWindow should return a valid pointer");

        SDL2.INSTANCE.SDL_Delay(3000); // Keep the window open for 3 seconds

        SDL2.INSTANCE.SDL_DestroyWindow(window);
        SDL2.INSTANCE.SDL_Quit();
    }
}

package main.java.com.pws.dryadengine.core.scripts;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import main.java.com.pws.dryadengine.core.App;
import main.java.com.pws.dryadengine.func.Window;
import main.java.com.pws.dryadengine.physics.CollisionManager_Rect2D;
import main.java.com.pws.dryadengine.types.Color;
import main.java.com.pws.dryadengine.types.Vector2;
import io.github.libsdl4j.api.hints.SDL_HintCallback;
import io.github.libsdl4j.api.hints.SdlHints;

public class Manager {
    private static final String SDL_HINT_RENDER_DRIVER = "SDL_RENDER_DRIVER";
    private static List<Script> scripts = new ArrayList<>();
    private static boolean changeListFlag = false;
    private static int[] scriptExecution = new int[] {0};
    private static List<Script> loadedScripts = new ArrayList<>();

    static {
        load();
    }

    private static void load() {
        try {
            File root = new File(App.executionFolder);
            for (File file : root.listFiles()){
                getFilesRecursive(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getFilesRecursive(File pFile) throws Exception {
        for(File files : pFile.listFiles()){
            if(files.isDirectory()){
                getFilesRecursive(files);
            } else {
                String data = Files.readString(Paths.get(files.getAbsolutePath()));
                if(!data.contains("extends Script")) continue;
                
                String pazz = data.split("package ")[1].split(";")[0];
                String clazz = data.split("class ")[1].split(" ")[0];
                
                // The corrected instantiation code
                String fullClassName = pazz + "." + clazz;
                Class<?> loadedClass = Class.forName(fullClassName);
                if (Script.class.isAssignableFrom(loadedClass)) {
                    @SuppressWarnings("unchecked")
                    Class<? extends Script> scriptClass = (Class<? extends Script>) loadedClass;
                    Script scriptInstance = scriptClass.getDeclaredConstructor().newInstance();
                    scripts.add(scriptInstance);
                }
            }
        }
    }

    public static void replant(int... newOrder) {
        changeListFlag = true;
        scriptExecution = newOrder;
    }

    public static void create() throws Exception {

        // Initialize all scripts with PC values
        for (Script script : scripts) {
            script.setPC();
        }

        // Sort scripts by PC value
        Collections.sort(scripts, Comparator.comparingInt(script -> script.pc));
        Window.title = "Test Platformer";
        Window.position = new Vector2(200, 200);
        Window.scale = new Vector2(1280, 720);
        Window.setBackgroundColor(new Color(255, 255, 255, 0));
        Window.updateWindow();

        while (!App.finnishExecution) {
            loadedScripts.clear();
            
            // Only add scripts that match the current execution order
            for (int pc : scriptExecution) {
                scripts.stream()
                    .filter(s -> s.pc == pc)
                    .forEach(loadedScripts::add);
            }
    
            // Plant phase
            for (Script script : loadedScripts) {
                script.plant();
            }
    
            changeListFlag = false;
    
            // Grow phase
            while (!changeListFlag) {
                Window.runEvents();
                Window.clear();

                for (Script script : loadedScripts) {
                    script.grow();
                }

                CollisionManager_Rect2D.getInstance().update();
                Window.drawBackground();
                Window.print();
                
            }
        }

        Window.end();
    }
    

    public static void addscript(Script toadd) {
        scripts.add(toadd);
    }

    public static List<Script> getLoadedScripts() {
        return loadedScripts;
    }
}

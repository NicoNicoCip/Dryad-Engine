package main.java.com.pws.dryadengine.core.scripts;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import main.java.com.pws.dryadengine.core.App;

public class ScriptManager {
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

    private static void getFilesRecursive(File pFile) {
        for(File files : pFile.listFiles()){
            if(files.isDirectory()){
                getFilesRecursive(files);
            } else {
                try {
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
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
   
    private static List<Script> changescriptExecution(int[] pcList) {
        List<Script> out = scripts;
        for (int i = 0; i < pcList.length; i++) {
            for (int j = 0; j < scripts.size(); j++) {
                if(scripts.get(j).pc == pcList[i]) {
                    out.add(scripts.get(j));
                }
            }
        }
        return out;
    }

    public static void replant(int... newOrder) {
        changeListFlag = true;
        scriptExecution = newOrder;
    }

    public static void create() throws Exception {
        // initialize the scene list, to reflect their pcs.
        scripts.sort((a, b) -> {
            if (a.pc == -1 && b.pc == -1) return 0;
            if (a.pc == -1) return -1;
            if (b.pc == -1) return 1;
            return Integer.compare(a.pc, b.pc);
        });

        while(!App.finnishExecution) {
            loadedScripts.clear();
            loadedScripts.addAll(changescriptExecution(scriptExecution));
            
            for (Script script : loadedScripts) {
                script.plant();
            }

            changeListFlag = false;

            while(!changeListFlag) {
                for (Script script : loadedScripts) {
                    script.grow();
                }
            }
        }
    }

    public static void addscript(Script toadd) {
        scripts.add(toadd);
    }

    public static List<Script> getLoadedScripts() {
        return loadedScripts;
    }
}

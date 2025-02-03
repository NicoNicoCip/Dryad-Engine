package main.java.pws.func;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.Scanner;

public abstract class FileManager {
    /*
     * the file manager will take the data from the app and save it as a file in a local data folder.
     * this files can be loaded later by the user by typing their name.
     * 
     * F1: a file that opens and writes something in a .txt
     * 
     * F2: read the contents as strings
     * 
     * F3: figure out JSON
     * 
     * F4: MONEY
     */


    public static final String getLocalRoute() {
        String relative = Paths.get("").toAbsolutePath().toString().replace("\\", "/") + "/../../../";
        return relative;
    }

    public static final File createFile(String route) {
        try {
            File file = new File(getLocalRoute() + route);
            return file;

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static final boolean writeToFile(File file, String text, boolean append) {
        try {
            FileWriter writer = new FileWriter(file, append);
            writer.write(text);
            writer.close();
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public static final String readFromFile(String route) {
        try {
            String out = "";
            Scanner scan = new Scanner(new File(getLocalRoute(), route));
            while (scan.hasNextLine()) {
                out += scan.nextLine() + "\n";
            }
            scan.close();

            out = out.substring(0, out.length()-1);
            return out;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static final boolean deleteFile(String route) {
        try {
            File file = new File(getLocalRoute(), route);
            file.delete();
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}

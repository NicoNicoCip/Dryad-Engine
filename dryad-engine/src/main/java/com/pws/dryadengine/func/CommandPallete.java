package com.pws.dryadengine.func;

import java.io.File;

import com.pws.dryadengine.core.App;

public abstract class CommandPallete {
    public static boolean runCommand(String command) {
        String[] commandRegex = command.split(" ");
        switch (commandRegex[0].toLowerCase()) {
            case "help":
                helpCommand();
                return true;

            case "save":
                saveCommand(commandRegex);
                return true;

            case "load":
                loadCommand(commandRegex);
                return true;
            
            case "delete":
                deleteCommand(commandRegex);
                return true;

            case "end":
                Debug.println("The program ended without issues.");
                return false;

            case "clear":
                System.out.print("\033[H\033[2J");
                System.out.flush();
                return true;

            case "dumplogs":
                LogsManager.deleteAllFile();
                return true;
            
            default:
                Debug.println("Something went wrong in chosing the command to run.");
                return true;
        }
    }

    private static void helpCommand() {
        Debug.print(
            """
            Write the commads that you want to do separated by a space:

            help    | shows all the command and what they do

            dumplogs| clears all the logs the folder while keeping the newest ones. 

            save    | used to save the currently loaded variables. write the name of the file after it.
                    > unfunctional. in the futurer you will be able to save a certain object type,
                    > not just the ccurrently loaded data.

            load    | used to load the data from a file. write the name of the file you want to load.
                    > unfunctional. in the future you will be able to load any file into any objet on the spot,
                    > or an entire file with multiple types.

            delete  | used to delete a file with the given name.

            end     | used to end the program.

            clear   | "clears" the screen. it just moves the window up to appear cleared.
            """);
    }

    private static void saveCommand(String[] regex) {
        if(regex.length == 1) {
            Debug.println(
            """
            save    | used o save the currently loaded variables. write the name of the file after it.
            """);
        } else {
            String data = "";

            /*
            for (Object l : App.bibioteca.getLibros()) {
                data += l.toData() + "\n";
            }
            */
            data += "ŸŽ";

            File f = FileManager.createFile(App.saveFileFolder + regex[1]);
            FileManager.writeToFile(f, data, false);

            Debug.println("Saved succesfully to route:" + FileManager.getLocalRoute() + App.saveFileFolder + regex[1]);
        }
    }

    private static void loadCommand(String[] regex) {
        if(regex.length == 1) {
            Debug.println(
            """
            load    | used to load the data from a file. write the name of the file you want to load.
            """);
        } else {
            try {
                String file = FileManager.readFromFile(App.saveFileFolder + regex[1]);
                String[] block = file.split("Ž");
                String[] line = null;

                if(!block[0].equals("Ÿ")) {
                    line = block[0].split("•");
                    for (int i = 0; i < line.length-1; i++) {
                        // add data to correspondding datatype
                    }
                }

                Debug.println("Loaded succesfully to route:" + FileManager.getLocalRoute() + App.saveFileFolder + regex[1]);
            } catch (Exception e) {
                Debug.println("The file could not be found or read.");
            }
        }
    }


    private static void deleteCommand(String[] regex) {
        if(regex.length == 1) {
            Debug.println(
            """
            delete  | used to delete a file with the given name.
            """);
        } else {
            FileManager.deleteFile(App.saveFileFolder + regex[1]);
            Debug.println("Deleted succesfully to route:" + FileManager.getLocalRoute() + App.saveFileFolder + regex[1]);
        }
    }
}

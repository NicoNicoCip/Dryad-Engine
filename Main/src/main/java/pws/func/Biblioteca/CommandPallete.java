package main.java.pws.func.Biblioteca;

import java.io.File;
import java.time.LocalDate;

import main.java.pws.core.Biblioteca.App;
import main.java.pws.core.Biblioteca.Biblioteca;
import main.java.pws.core.Biblioteca.Libro;
import main.java.pws.core.Biblioteca.Reserva;
import main.java.pws.core.Biblioteca.Trabajador;
import main.java.pws.core.Biblioteca.Usuario;
import main.java.pws.func.Debug;
import main.java.pws.func.FileManager;
import main.java.pws.func.LogsManager;

public abstract class CommandPallete {
    public static boolean runCommand(String command) {
        String[] commandRegex = command.split(" ");
        switch (commandRegex[0].toLowerCase()) {
            case "help":
                helpCommand();
                return true;

            case "sandbox":
                sandboxCommand(commandRegex);
                return true;

            case "create":
                createCommand(commandRegex);
                return true;

            case "update":
                return updateCommand(commandRegex);

            case "remove":
                removeCommand(commandRegex);
                return true;

            case "check":
                checkCommand(commandRegex);
                return true;

            case "checkall":
                chechAllCommand(commandRegex);
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
                Debug.println("END;");
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

            dumplogs| clears all the logs the folder while keeping  

            sandbox | used to run the sandbox functions in the app class:
                    > help          | displays the commands for the specific sandboxes.
                    > createSandbox | creates a few default variables to use
                    > printSandbox  | prints the contents of the variables and their ids
                    > fileSandbox   | used to text creation, reading, writing and deletion of a txt file.
                    > all           | does all 3 sandbox commands at once

            create  | used to create a new variable. after selecting the variable type, write separated by a
                    | space directly the data that you want to write. not writing all the data will show you
                    | the types and names of the data thats needed.
                    > libro         | creates a "Libro(String titulo, String autor, int paginas, String ISBN, int ejemplares)"
                    > reserva       | creates a "Reserva(Srting idLibro, String idUsuario, int diasReserva)"
                    > usuario       | craetes a "Usuario(String nombre, String direccion, String telefono, String fechaNacimiento, String estado)"
                    > trabajador    | creates a "Trabajador(String nombre, String direccion, String telefono, LocalDate fechaNacimiento, String nuss, int salario)"

            remove  | used to remove a variable. you need to use its id.
                    > id            | the id of the object you want to remove

            update  | used to update the data of a variable. after selecting the variable type, write
                    | separated by a space directly the data that you want to write. not writing all the
                    | data will show you the types and names of the data thats needed.
                    > id            | the id of the object you want to update

            check   | used to check variable. you need to use its id.
                    > id            | the id of the object you want to check

            checkAll| used to check all the variables. if you write it on its own, it will print all the
                    | variables loaded. if followed by a type it will print all the variables of that type.
                    > full           | check all of everything in "Biblioteca"
                    > libros         | check all "Libros" in "Biblioteca"
                    > reservas       | check all "Reservas" in "Biblioteca"
                    > usuarios       | check all "Usuarios" in "Biblioteca"
                    > trabajadores   | check all "Trabajadores" in "Biblioteca"


            save    | used to save the currently loaded variables. write the name of the file after it.

            load    | used to load the data from a file. write the name of the file you want to load.

            delete  | used to delete a file with the given name.

            end     | used to end the program.

            clear   | "clears" the screen. it just moves the window to appear cleared.
            """);
    }

    private static void sandboxCommand(String[] regex) {
        if(regex.length == 1) {
            Debug.println(
            """
            sandbox | used to run the sandbox functions in the app class:
                    > help          | displays the commands for the specific sandboxes.
                    > createSandbox | creates a few default variables to use
                    > printSandbox  | prints the contents of the variables and their ids
                    > fileSandbox   | used to text creation, reading, writing and deletion of a txt file.
                    > all           | does all 3 sandbox commands at once
            """);
        } else {
            switch (regex[1].toString().toLowerCase()) {

                case "createsandbox":
                    App.createSandbox();
                    Debug.println("Succesfuly created sandbox.");
                break;
    
                case "printsandbox":
                    App.debugPrintSandbox();
                break;
    
                case "filesandbox":
                    App.debugFileSandbox();
                break;
    
                case "all":
                    App.createSandbox();
                    App.debugPrintSandbox();
                    App.debugFileSandbox();
                break;
            
                default:
                break;
            }
        }
    }

    private static void createCommand(String[] regex) {
        if(regex.length == 1) {
            Debug.println(
            """
            create  | used to create a new variable. after selecting the variable type, write separated by a
                    | space directly the data that you want to write. not writing all the data will show you
                    | the types and names of the data thats needed.
                    > libro         | creates a "Libro(String titulo, String autor, int paginas, String ISBN, int ejemplares)"
                    > reserva       | creates a "Reserva(Srting idLibro, String idUsuario, int diasReserva)"
                    > usuario       | craetes a "Usuario(String nombre, String direccion, String telefono, String fechaNacimiento, String estado)"
                    > trabajador    | creates a "Trabajador(String nombre, String direccion, String telefono, LocalDate fechaNacimiento, String nuss, int salario)"

            !!!!   DONT PUT TYPES, PARENTHESIS, OR COMMAS   !!!!
            !!!! For 'fecha nacimiento' you have to write in format YYYY-MM-DD, replacing the leters with the date and keeping the dashes. !!!!
            """);
        } else {
            switch (regex[1].toString().toLowerCase()) {
                case "libro":
                    if (regex.length < 7) {
                        Debug.println("Libro(String titulo, String autor, int paginas, String ISBN, int ejemplares)");
                        Debug.println("   !!!!   DONT PUT TYPES, PARENTHESIS, OR COMMAS   !!!!");
                    } else {
                        Libro l = createLibroFromData(regex,2);
                        App.bibioteca.addLibro(l);
    
                        Debug.println("The 'libro' was created succesfuly:");
                        Debug.println(l.toString());
                    }
                break;
    
                case "reserva":
                    if (regex.length != 5) {
                        Debug.println("Reserva(Srting idLibro, String idUsuario, int diasReserva)");
                        Debug.println("   !!!!   DONT PUT TYPES, PARENTHESIS, OR COMMAS   !!!!");
                    } else {
                        Reserva r = creaeteReservaFromData(regex,2);
                        App.bibioteca.addReserva(r);
    
                        Debug.println("The 'reserva' was created succesfuly:");
                        Debug.println(r.toString());
                    }
                break;
    
                case "usuario":
                    if (regex.length != 7) {
                        Debug.println("Usuario(String nombre, String direccion, String telefono, String fechaNacimiento, String estado)");
                        Debug.println("   !!!!   DONT PUT TYPES, PARENTHESIS, OR COMMAS   !!!!");
                        Debug.println("For 'fecha nacimiento' you have to write in format YYYY-MM-DD, replacing the leters with the date and keeping the dashes.");
                    } else {
                        Usuario u = createUsuarioFromData(regex,2);
                        App.bibioteca.addUsuario(u);
                        Debug.println("The 'usuario' was created succesfuly:");
                        Debug.println(u.toString());
                    }
                break;
    
                case "trabajador":
                    if (regex.length != 8) {
                        Debug.println("Trabajador(String nombre, String direccion, String telefono, LocalDate fechaNacimiento, String nuss, int salario)");
                        Debug.println("   !!!!   DONT PUT TYPES, PARENTHESIS, OR COMMAS   !!!!");
                        Debug.println("For 'fecha nacimiento' you have to write in format YYYY-MM-DD, replacing the leters with the date and keeping the dashes.");
                    } else {
                        Trabajador t = createTrabajadorFromData(regex,2);
                        App.bibioteca.addTrabajador(t);
    
                        Debug.println("The 'trabajador' was created succesfuly:");
                        Debug.println(t.toString());
                    }
                break;
    
                default:
                    Debug.println("Something went wrong in 'create' command");
                break;
            }
        }
    }

    private static void removeCommand(String[] regex) {
        if(regex.length == 1) {
            Debug.println(
            """
            remove  | used to remove a variable. you need to use its id.
                    > id            | the id of the object you want to remove
            """);
        } else if(regex.length == 2){
            int serial = Integer.valueOf(regex[1]);
            if (App.bibioteca.removeByID(serial)) {
                Debug.println("Succesfuly removed " + regex[1].toString());
            } else {
                Debug.println("Failed to remove " + regex[1].toString());
            }
        }
    }

    private static boolean updateCommand(String[] regex) {
        if(regex.length == 1) {
            Debug.println(
            """
            update  | used to update the data of a variable. after selecting the variable type, write
                    | separated by a space directly the data that you want to write. not writing all the
                    | data will show you the types and names of the data thats needed.
                    > id            | the id of the object you want to update
            """);
        } else {
            int serial = Integer.valueOf(regex[1]);
            Object o = App.bibioteca.getByID(serial);

            if(o == null) {
                Debug.println("No object of that id found.");
                return true;
            }

            if(o.getClass() == Libro.class)
            {
                if (regex.length != 7) {
                    Debug.println("Libro(String titulo, String autor, int paginas, String ISBN, int ejemplares)");
                    Debug.println("   !!!!   DONT PUT TYPES, PARENTHESIS, OR COMMAS   !!!!");
                } else {
                    App.bibioteca.getLibroByID(serial).cambiarDatos(
                        regex[2].toString(), 
                        regex[3].toString(),
                        Integer.parseInt(regex[4]), 
                        regex[5].toString(), 
                        Integer.parseInt(regex[6])
                    );
                    
                    Debug.println("The 'libro' was updated succesfuly.");
                    Debug.println(App.bibioteca.getLibroByID(serial).toString());
                }
            } 
            
            else if(o.getClass() == Reserva.class) {
                if (regex.length != 7) {
                    Debug.println("Reserva(Srting idLibro, String idUsuario, int diasReserva)");
                    Debug.println("   !!!!   DONT PUT TYPES, PARENTHESIS, OR COMMAS   !!!!");
                } else {
                    App.bibioteca.getReservaByID(serial).cambiarDatos(
                        Integer.parseInt(regex[2].toString()),
                        Integer.parseInt(regex[3].toString()),
                        Integer.parseInt(regex[4].toString())
                    );
                    
                    Debug.println("The 'reserva' was updated succesfuly:");
                    Debug.println(App.bibioteca.getReservaByID(serial).toString());
                }
            } 
            
            else if (o.getClass() == Usuario.class) {
                if (regex.length != 7) {
                    Debug.println("Usuario(String nombre, String direccion, String telefono, String fechaNacimiento, String estado)");
                    Debug.println("   !!!!   DONT PUT TYPES, PARENTHESIS, OR COMMAS   !!!!");
                    Debug.println("For 'fecha nacimiento' you have to write in format YYYY-MM-DD, replacing the leters with the date and keeping the dashes.");
                } else {
                    App.bibioteca.getUsuarioByID(serial).cambiarDatos(
                        regex[2].toString(),
                        regex[3].toString(),
                        regex[4].toString(),
                        LocalDate.of(
                                Integer.parseInt(regex[5].split("-")[0]),
                                Integer.parseInt(regex[5].split("-")[1]),
                                Integer.parseInt(regex[5].split("-")[2])),
                        Usuario.getEstadoFromString(regex[6].toString().toUpperCase())
                    );
                    
                    Debug.println("The 'usuario' was updated succesfuly:");
                    Debug.println(App.bibioteca.getUsuarioByID(serial).toString());
                }
            } 
            
            else if (o.getClass() == Trabajador.class) {
                if (regex.length != 7) {
                    Debug.println("Trabajador(String nombre, String direccion, String telefono, LocalDate fechaNacimiento, String nuss, int salario)");
                    Debug.println("   !!!!   DONT PUT TYPES, PARENTHESIS, OR COMMAS   !!!!");
                    Debug.println("For 'fecha nacimiento' you have to write in format YYYY-MM-DD, replacing the leters with the date and keeping the dashes.");
                } else {
                    App.bibioteca.getTrabajadorByID(serial).cambiarDatos(
                        regex[2].toString(),
                        regex[3].toString(),
                        regex[4].toString(),
                        LocalDate.of(
                                Integer.parseInt(regex[5].split("-")[0]),
                                Integer.parseInt(regex[5].split("-")[1]),
                                Integer.parseInt(regex[5].split("-")[2])),
                        regex[6].toString(),
                        Integer.parseInt(regex[7].toString())
                    );

                    Debug.println("The 'trabajador' was updated succesfuly:");
                    Debug.println(App.bibioteca.getUsuarioByID(serial).toString());
                }
            }
        }

        return true;
    }

    private static void checkCommand(String[] regex) {
        if(regex.length == 1) {
            Debug.println(
            """
            check   | used to check variable. you need to use its id.
                    > id            | the id of the object you want to check
            """);
        } else {
            Debug.println(App.bibioteca.getByID(Integer.valueOf(regex[1])).toString());
        }
    }

    private static void chechAllCommand(String[] regex) {
        if(regex.length == 1) {
            Debug.println(
            """
            checkAll| used to check all the variables. if you write it on its own, it will print all the
                    | variables loaded. if followed by a type it will print all the variables of that type.
                    v
                    > libros         | check all "Libros" in "Biblioteca"
                    > reservas       | check all "Reservas" in "Biblioteca"
                    > usuarios       | check all "Usuarios" in "Biblioteca"
                    > trabajadores   | check all "Trabajadores" in "Biblioteca"
            """);
        } else {
            switch (regex[1].toString().toLowerCase()) {

                case "full":
                    App.bibioteca.printAllLibros();
                break;

                case "libros":
                    App.bibioteca.printAllLibros();
                break;

                case "reservas":
                    App.bibioteca.printAllReserva();
                break;

                case "usuarios":
                    App.bibioteca.printAllUsuarios();
                break;

                case "trabajadores":
                    App.bibioteca.printAllTrabajadores();
                break;
            
                default:
                break;
            }
        }
    }

    private static void saveCommand(String[] regex) {
        if(regex.length == 1) {
            Debug.println(
            """
            save    | used o save the currently loaded variables. write the name of the file after it.
            """);
        } else {
            String data = "";

            for (Libro l : App.bibioteca.getLibros()) {
                data += l.toData() + "\n";
            }
            data += "ŸŽ";

            for (Usuario l : App.bibioteca.getUsuarios()) {
                data += l.toData() + "\n";
            }
            data += "ŸŽ";

            for (Trabajador l : App.bibioteca.getTrabajadores()) {
                data += l.toData() + "\n";
            }
            data += "ŸŽ";

            for (Reserva l : App.bibioteca.getReservas()) {
                data += l.toData().replace("•", "") + "•\n";
            }
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
                App.bibioteca = new Biblioteca();
                String file = FileManager.readFromFile(App.saveFileFolder + regex[1]);
                String[] block = file.split("Ž");
                String[] line = null;

                if(!block[0].equals("Ÿ")) {
                    line = block[0].split("•");
                    for (int i = 0; i < line.length-1; i++) {
                        App.bibioteca.addLibro(createLibroFromData(line[i].split("…"), 1));
                    }
                }

                if(!block[1].equals("Ÿ")) {
                    line = block[1].split("•");
                    for (int i = 0; i < line.length-1; i++) {
                        App.bibioteca.addUsuario(createUsuarioFromData(line[i].split("…"), 1));
                    }
                }

                if(!block[2].equals("Ÿ")) {
                    line = block[2].split("•");
                    for (int i = 0; i < line.length-1; i++) {
                        App.bibioteca.addTrabajador(createTrabajadorFromData(line[i].split("…"), 1));
                    }
                }

                if(!block[3].equals("Ÿ")) {
                    line = block[3].split("•");
                    for (int i = 0; i < line.length-1; i++) {
                        String[] vars = line[i].split("…");
                        App.bibioteca.addReserva(new Reserva(
                            Integer.valueOf(vars[1]),
                            Integer.valueOf(vars[8]),
                            Integer.valueOf(vars[15])
                        ));
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

    private static Libro createLibroFromData(String[] chain, int offset) {
        Libro l = null;

        if(chain.length == 7) {
            l = new Libro(
                chain[0 + offset].toString(),
                chain[1 + offset].toString(),
                Integer.parseInt(chain[2 + offset]),
                chain[3 + offset].toString(),
                Integer.parseInt(chain[4 + offset])
                );
        } else if(chain.length == 8) {
            l = new Libro(
                chain[2].toString(),
                chain[3].toString(),
                Integer.parseInt(chain[4]),
                chain[5].toString(),
                Integer.parseInt(chain[6]),
                Integer.parseInt(chain[7])
                );
        }

        return l;
    }

    private static Reserva creaeteReservaFromData(String[] chain, int offset) {
        return new Reserva(
            Integer.parseInt(chain[0 + offset].toString()),
            Integer.parseInt(chain[1 + offset].toString()),
            Integer.parseInt(chain[2 + offset].toString()));
    }

    private static Usuario createUsuarioFromData(String[] chain, int offset) {
        return new Usuario(
            chain[0 + offset].toString(),
            chain[1 + offset].toString(),
            chain[2 + offset].toString(),
            LocalDate.of(
                    Integer.parseInt(chain[3 + offset].split("-")[0]),
                    Integer.parseInt(chain[3 + offset].split("-")[1]),
                    Integer.parseInt(chain[3 + offset].split("-")[2])),
            Usuario.getEstadoFromString(chain[4 + offset])
            );
    }

    private static Trabajador createTrabajadorFromData(String[] chain, int offset) {
        return new Trabajador(
            chain[0 + offset].toString(),
            chain[1 + offset].toString(),
            chain[2 + offset].toString(),
            LocalDate.of(
                    Integer.parseInt(chain[3 + offset].split("-")[0]),
                    Integer.parseInt(chain[3 + offset].split("-")[1]),
                    Integer.parseInt(chain[3 + offset].split("-")[2])),
            chain[4 + offset].toString(),
            Integer.parseInt(chain[5 + offset].toString())
            );
    }
}

package com.pws.dryadengine.core.Biblioteca;

import java.io.File;
import java.time.LocalDate;
import java.util.Scanner;

import com.pws.dryadengine.func.Debug;
import com.pws.dryadengine.func.FileManager;
import com.pws.dryadengine.func.ID;
import com.pws.dryadengine.func.LogsManager;
import com.pws.dryadengine.func.Biblioteca.CommandPallete;

public class App {
    private static final ID id = new ID(0);
    public static final String saveFileFolder = "/Main/data/saves/";
    private static Scanner scan = new Scanner(System.in);
    public static Biblioteca bibioteca = new Biblioteca();

    /* type ids:
     * 0 = main
     * 1 = Libro
     * 2 = Usuario
     * 3 = Trabajador
     * 4 = Reserva
     * 5 = Biblioteca
     */

    public static final void createSandbox()
    {
        bibioteca.addUsuario(new Usuario("Mamon", "Flores 2", "641 69 69 42", LocalDate.of(1998, 5, 13), Usuario.Estado.ACTIVO));
        bibioteca.addUsuario(new Usuario("Pepe", "Calle Luna 45", "642 55 33 22", LocalDate.of(2005, 1, 5), Usuario.Estado.ACTIVO));
        bibioteca.addUsuario(new Usuario("Lupita", "Av. Sol 12", "643 77 88 99", LocalDate.of(1980, 8, 21), Usuario.Estado.BAJA));

        bibioteca.addLibro(new Libro("Juancito", "Juan", 100, "1241254451456", 3));
        bibioteca.addLibro(new Libro("El Principito", "Mamon", 300, "125452668542148", 10));
        bibioteca.addLibro(new Libro("Don Quijote", "Miguel de Cervantes", 500, "1234567890123", 5));
        bibioteca.addLibro(new Libro("Cien Años de Soledad", "Gabriel García Márquez", 417, "9876543210987", 7));
        bibioteca.addLibro(new Libro("1984", "George Orwell", 328, "1122334455667", 4));
        bibioteca.addLibro(new Libro("Moby Dick", "Herman Melville", 635, "2233445566778", 2));
        bibioteca.addLibro(new Libro("Hamlet", "William Shakespeare", 160, "3344556677889", 8));
        bibioteca.addLibro(new Libro("The Odyssey", "Homer", 300, "4455667788990", 3));
        bibioteca.addLibro(new Libro("Pride and Prejudice", "Jane Austen", 279, "5566778899001", 6));
        bibioteca.addLibro(new Libro("The Hobbit", "J.R.R. Tolkien", 310, "6677889900112", 9));
        bibioteca.addLibro(new Libro("Crime and Punishment", "Fyodor Dostoevsky", 430, "7788990011223", 4));
        bibioteca.addLibro(new Libro("To Kill a Mockingbird", "Harper Lee", 281, "8899001122334", 7));

        bibioteca.addReserva(new Reserva(10014, 10022, 7));
        bibioteca.addReserva(new Reserva(10014, 10021, 14));
    }

    public static final void debugPrintSandbox()
    {
        System.out.println("Introducir el modo de sorteo: N (Ninguno), T (Titulo), P (Pagina). Introducir Cualquier otra para acabar.");
        String in = scan.next();
        switch (in.toString().toUpperCase()) {
            case "N" ->{
                bibioteca.debugPrintAll(Biblioteca.SortingType.NONE);
            }
            case "T" ->{
                bibioteca.debugPrintAll(Biblioteca.SortingType.BYTITLE);
            }
            case "P" ->{
                bibioteca.debugPrintAll(Biblioteca.SortingType.BYPAGES);
            }
            default -> {
                System.out.println("---------Programa finalizada----------");
                System.out.println("O porque tu has quierido, o porque has teclado algo que no funcciona.");
                System.out.println("Has teclado: '" + in + "'.");
            }
        }
    }

    public static final void debugFileSandbox() {
        File f = FileManager.createFile("/Biblioteca/Main/data/testfile.txt");
        if(f != null) 
            Debug.println("The file was succesfuly created.");

        if(FileManager.writeToFile(f, "This is some test text. \n" + 
                                      "This is text on another line.", false)) 
            Debug.println("The text was written to the file.");

        String fileText = FileManager.readFromFile("/Biblioteca/Main/data/testfile.txt");
        Debug.println(fileText);

        if(FileManager.deleteFile("/Biblioteca/Main/data/testfile.txt"))
            Debug.println("The file was succesfuly deleted.");
    }

    public static final void createAppEnv(Scanner scan) {
        Debug.println("Started Aplication.");
        Debug.println("Write 'help' for a list of all the commands.");

        while (true) {
            Debug.println("----------------");
            Debug.print("\n>> ");

            String scanOut = scan.nextLine();
            LogsManager.log(scanOut);

            if(!CommandPallete.runCommand(scanOut)){
                break;
            }
        }
    }

    public int getId() {
        return id.getSerial();
    }

    public static void main(String[] args) {
        try {
            Scanner scan = new Scanner(System.in);
            LogsManager.createLogFile();
            // Uncommment the lower line if you want to autoad data to test.
            //createSandbox();
            createAppEnv(scan);
    
            scan.close();
        } catch (Exception e) {
            Debug.println(e);
            for (StackTraceElement st : e.getStackTrace()) {
                Debug.print(st.toString()); System.out.println();
            }
        }
    }
}

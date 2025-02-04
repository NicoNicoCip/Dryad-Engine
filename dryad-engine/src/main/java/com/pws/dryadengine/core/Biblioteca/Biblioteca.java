package main.java.com.pws.dryadengine.core.Biblioteca;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import main.java.com.pws.dryadengine.func.Debug;
import main.java.com.pws.dryadengine.func.ID;

public class Biblioteca extends Object{
    private final ID id = new ID(5);
    private List<Libro> Libros = new ArrayList<>();
    private List<Usuario> Usuarios = new ArrayList<>();
    private List<Trabajador> Trabajadores = new ArrayList<>();
    private List<Reserva> Reservas = new ArrayList<>();

    public enum SortingType{
        NONE,
        BYTITLE,
        BYPAGES
    }

    //construccions-----------------------------------

    public Biblioteca() {
    }

    public Biblioteca(Biblioteca bibioteca) {
        this.Libros = bibioteca.Libros;
        this.Usuarios = bibioteca.Usuarios;
    }


    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", Libros='" + getLibros() + "'" +
            ", Usuarios='" + getUsuarios() + "'" +
            ", Trabajadores='" + getTrabajadores() + "'" +
            ", Reservas='" + getReservas() + "'" +
            "}";
    }
    //funccions------------------------------------------------------

    public List<Libro> sortByTitle() {
        return Libros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .collect(Collectors.toList());
    }

    public List<Libro> sortByPages() {
        return Libros.stream()
                .sorted(Comparator.comparingInt(Libro::getPaginas))
                .collect(Collectors.toList());
    }

    public void debugPrintAll(SortingType sortingType) {
        switch(sortingType)
        {
            case NONE:
                printAllLibros();
            break;
            case BYPAGES:
                printAllLibros(sortByPages());
            break;
            case BYTITLE:
                printAllLibros(sortByTitle());
            break;
        }
        printAllUsuarios();
        printAllTrabajadores();
        printAllReserva();
    }

    public void printAllLibros() {
        for (Libro libro : Libros) {
            Debug.println(libro);
        }
    }

    public void printAllLibros(List<Libro> modified) {
        for (Libro libro : modified) {
            Debug.println(libro);
        }
    }

    public void printAllUsuarios()
    {
        for (Usuario usuario : Usuarios) {
            Debug.println(usuario);
        }
    }

    public void printAllTrabajadores()
    {
        for (Trabajador trabajadores : Trabajadores) {
            Debug.println(trabajadores);
        }
    }

    public void printAllReserva()
    {
        for (Reserva reserva : Reservas) {
            Debug.println(reserva);
        }
    }

    public List<Usuario> getUsuariosSancionables() {
        List<Usuario> u = new ArrayList<>();
        for (Reserva reserva : Reservas) {
            if(reserva.esSobreReserva())
                u.add(reserva.getUsuario());
        }
        return u;
    }

    public List<Reserva> getReservsaSancionables() {
        List<Reserva> r = new ArrayList<>();
        for (Reserva reserva : Reservas ) {
            if(reserva.esSobreReserva())
                r.add(reserva);
        }
        return r;
    }

    public Libro getLibroByID(int id) {
        for (Libro l : Libros) {
            if(l.getId() == id)
                return l;
        }
        return null;
    }

    public Usuario getUsuarioByID(int id) {
        for (Usuario u : Usuarios) {
            if(u.getId() == id)
                return u;
        }
        return null;
    }

    public Trabajador getTrabajadorByID(int id) {
        for (Trabajador t : Trabajadores) {
            if(t.getId() == id)
                return t;
        }
        return null;
    }

    public Reserva getReservaByID(int id) {
        for (Reserva r : Reservas) {
            if(r.getId() == id)
                return r;
        }
        return null;
    }

    public Object getByID(int id) {
        Libro l = getLibroByID(id);
        if(l != null) return l;

        Usuario u = getUsuarioByID(id);
        if(u != null) return u;

        Trabajador t = getTrabajadorByID(id);
        if(t != null) return t;

        Reserva r = getReservaByID(id);
        if(r != null) return r;

        return null;
    }

    public boolean removeByID(int id) {
        Object o = getByID(id);
        if(o.getClass() == Libro.class){
            removeLibro((Libro)o);
            return true;
        }
        else if(o.getClass() == Usuario.class){
            removeUsuario((Usuario)o);
            return true;
        }
        else if(o.getClass() == Trabajador.class){
            removeTrabajador((Trabajador)o);
            return true;
        }
        else if(o.getClass() == Reserva.class){
            removeReserva((Reserva)o);
            return true;
        }
        return false;
    }

    //getters and setters----------------------------

    public int getId() {
        return id.getSerial();
    }

    public List<Libro> getLibros() {
        return this.Libros;
    }

    public void setLibros(List<Libro> Libros) {
        this.Libros = Libros;
    }

    public List<Usuario> getUsuarios() {
        return this.Usuarios;
    }

    public void setUsuarios(List<Usuario> Usuarios) {
        this.Usuarios = Usuarios;
    }

    public List<Reserva> getReservas() {
        return this.Reservas;
    }

    public void setReservas(List<Reserva> Reservas) {
        this.Reservas = Reservas;
    }

    public List<Trabajador> getTrabajadores() {
        return this.Trabajadores;
    }

    public void setTrabajadores(List<Trabajador> Trabajadores) {
        this.Trabajadores = Trabajadores;
    }
    
    //adders and removers------------------------------

    public void addLibro(Libro libro){
        Libros.add(libro);
    }

    public void removeLibro(Libro libro){
        Libros.remove(libro);
    }

    public void addUsuario(Usuario usuario){
        Usuarios.add(usuario);
    }

    public void removeUsuario(Usuario usuario){
        Usuarios.remove(usuario);
    }

    public void addReserva(Reserva reservas){
        Reservas.add(reservas);
    }

    public void removeReserva(Reserva reservas){
        Reservas.remove(reservas);
    }

    public void addTrabajador(Trabajador reservas){
        Trabajadores.add(reservas);
    }

    public void removeTrabajador(Trabajador reservas){
        Trabajadores.remove(reservas);
    }
}

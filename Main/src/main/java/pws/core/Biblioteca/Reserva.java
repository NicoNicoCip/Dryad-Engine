package main.java.pws.core.Biblioteca;
import java.time.LocalDate;

import main.java.pws.func.ID;

public class Reserva extends Object{
    private final ID id = new ID(4);
    private Libro libro;
    private Usuario usuario;
    private LocalDate fecha;
    private int diasReserva;

    //construccions-----------------------------------

    public Reserva() {
        this.fecha = LocalDate.now();
    }

    public Reserva(int idLibro, int idUsuario, int diasReserva) {
        this.libro = App.bibioteca.getLibroByID(idLibro);
        this.usuario = App.bibioteca.getUsuarioByID(idUsuario);
        this.fecha = LocalDate.now();
        this.diasReserva = diasReserva;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", libro='" + getLibro() + "'" +
            ", usuario='" + getUsuario() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", diasReserva='" + getDiasReserva() + "'" +
            "}";
    }

    public String toData() {
        return
            getId() + "…" +
            getLibro().toData() + "…" +
            getUsuario().toData() + "…" +
            getFecha() + "…" +
            getDiasReserva() + "•";
    }

    //funccions------------------------------------------------------

    public void cambiarDatos(int idLibro, int idUsuario, int diasReserva) {
        this.libro = App.bibioteca.getLibroByID(idLibro);
        this.usuario = App.bibioteca.getUsuarioByID(idUsuario);
        this.fecha = LocalDate.now();
        this.diasReserva = diasReserva;
    }

    public boolean esSobreReserva()
    {
        if(tiempoQuedado().getDayOfMonth() < 0)
            return true;
        else
            return false;
    }

    public LocalDate tiempoQuedado()
    {
        LocalDate current = LocalDate.now();
        return fecha.plusDays(diasReserva).minusDays(current.getDayOfMonth());
    }

    //getters and setters----------------------------


    public int getId() {
        return id.getSerial();
    }

    public Libro getLibro() {
        return this.libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getDiasReserva() {
        return this.diasReserva;
    }

    public void setDiasReserva(int diasReserva) {
        this.diasReserva = diasReserva;
    }
}

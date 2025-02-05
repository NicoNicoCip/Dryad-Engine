package com.pws.dryadengine.core.Biblioteca;

import com.pws.dryadengine.func.ID;

public class Libro extends Object {
    private final ID id = new ID(1);    
    private String titulo;
    private String autor;
    private int paginas;
    private String ISBN;
    private int ejemplares;
    private int disponibles;

    //construccions-----------------------------------

    public Libro() {
    }

    public Libro(String titulo, String autor, int paginas, String ISBN, int ejemplares) {
        this.titulo = titulo;
        this.autor = autor;
        this.paginas = paginas;
        this.ISBN = ISBN;
        this.ejemplares = ejemplares;
        this.disponibles = ejemplares;
    }

    public Libro(String titulo, String autor, int paginas, String ISBN, int ejemplares, int disponibles) {
        this.titulo = titulo;
        this.autor = autor;
        this.paginas = paginas;
        this.ISBN = ISBN;
        this.ejemplares = ejemplares;
        this.disponibles = disponibles;
    }

    public Libro(Libro libro) {
        this.titulo = libro.titulo;
        this.autor = libro.autor;
        this.paginas = libro.paginas;
        this.ISBN = libro.ISBN;
        this.ejemplares = libro.ejemplares;
        this.disponibles = libro.disponibles;
    }


    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", autor='" + getAutor() + "'" +
            ", paginas='" + getPaginas() + "'" +
            ", ISBN='" + getISBN() + "'" +
            ", ejemplares='" + getEjemplares() + "'" +
            ", disponibles='" + getDisponibles() + "'" +
            "}";
    }

    public String toData() {
        return
            getId() + "…" +
            getTitulo() + "…" +
            getAutor() + "…" +
            getPaginas() + "…" +
            getISBN() + "…" +
            getEjemplares() + "…" +
            getDisponibles() + "•";
    }


    //funccions------------------------------------------------------

    public void cambiarDatos(String titulo, String autor, int paginas, String ISBN, int ejemplares) {
        this.titulo = titulo;
        this.autor = autor;
        this.paginas = paginas;
        this.ISBN = ISBN;
        this.ejemplares = ejemplares;
    }

    public boolean disponibles(){
        if(this.disponibles > 0)
            return true;
        else
            return false;
    }

    public boolean prestar(){
        if(disponibles())
        {
            this.disponibles--;
            return true;
        }
        else
            return false;
    }

    public void devolver(){
        this.disponibles++;
    }

    public void annadirEjemplar(int nº){
        this.ejemplares += nº;
        this.disponibles += nº;
    }

    public void quitarEjemplar(int nº){
        if(nº >= 0)
        {
            this.ejemplares -= nº;
            this.disponibles -= nº;
        }
        else
        {
            this.ejemplares = 0;
            this.disponibles = 0;
        }
    }

    //getters and setters----------------------------

    public int getId() {
        return id.getSerial();
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return this.autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getPaginas() {
        return this.paginas;
    }

    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }

    public String getISBN() {
        return this.ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getEjemplares() {
        return this.ejemplares;
    }

    public void setEjemplares(int ejemplares) {
        this.ejemplares = ejemplares;
    }

    public int getDisponibles() {
        return this.disponibles;
    }

    public void setDisponibles(int disponibles) {
        this.disponibles = disponibles;
    }

}

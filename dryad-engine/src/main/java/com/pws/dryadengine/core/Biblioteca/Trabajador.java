package com.pws.dryadengine.core.Biblioteca;

import java.time.LocalDate;

import com.pws.dryadengine.func.ID;

public class Trabajador extends Persona{
    private final ID id = new ID(3);
    private String nuss;
    private int salario;


    public Trabajador() {
    }

    public Trabajador(String nombre, String direccion, String telefono, LocalDate fechaNacimiento, String nuss, int salario) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.nuss = nuss;
        this.salario = salario;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            super.toString() +
            ", nuss='" + getNuss() + "'" +
            ", salario='" + getSalario() + "'" +
            "}";
    }

    @Override
    public String toData() {
        return
            getId() + "…" +
            super.toData() +
            getNuss() + "…" +
            getSalario() + "•";
    }

    public void cambiarDatos(String nombre, String direccion, String telefono, LocalDate fechaNacimiento, String nuss, int salario) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.nuss = nuss;
        this.salario = salario;
    }

    public String getNuss() {
        return this.nuss;
    }

    public void setNuss(String nuss) {
        this.nuss = nuss;
    }

    public int getSalario() {
        return this.salario;
    }

    public void setSalario(int salario) {
        this.salario = salario;
    }

    public long getId() {
        return id.getSerial();
    }
}

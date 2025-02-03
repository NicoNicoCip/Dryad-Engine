package main.java.pws.core.Biblioteca;

import java.time.LocalDate;

public abstract class Persona {
    protected String nombre;
    protected String direccion;
    protected String telefono;
    protected LocalDate fechaNacimiento;

    protected String getNombre() {
        return this.nombre;
    }

    protected void setNombre(String nombre) {
        this.nombre = nombre;
    }

    protected String getDireccion() {
        return this.direccion;
    }

    protected void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    protected String getTelefono() {
        return this.telefono;
    }

    protected void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    protected LocalDate getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    protected void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    protected Persona nombre(String nombre) {
        setNombre(nombre);
        return this;
    }

    protected Persona direccion(String direccion) {
        setDireccion(direccion);
        return this;
    }

    protected Persona telefono(String telefono) {
        setTelefono(telefono);
        return this;
    }

    protected Persona fechaNacimiento(LocalDate fechaNacimiento) {
        setFechaNacimiento(fechaNacimiento);
        return this;
    }

    @Override
    public String toString() {
        return
            " nombre='" + getNombre() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'";
    }

    public String toData() {
        return
            getNombre() + "…" +
            getDireccion() + "…" +
            getTelefono() + "…" +
            getFechaNacimiento() + "…";
    }
}

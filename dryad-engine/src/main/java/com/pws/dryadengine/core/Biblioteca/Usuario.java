package com.pws.dryadengine.core.Biblioteca;

import java.time.LocalDate;

import com.pws.dryadengine.func.Debug;
import com.pws.dryadengine.func.ID;

public class Usuario extends Persona {
    private final ID id = new ID(2);
    private Estado estado;

    public enum Estado {
        NADA,
        ACTIVO,
        BAJA,
        SUSPENDIDO,
        SANCIONADO
    }

    //construccions-----------------------------------

    public Usuario() {
        this.nombre = "";
        this.direccion = "";
        this.telefono = "";
        this.fechaNacimiento = LocalDate.now();
        this.estado = Estado.NADA;
    }

    public Usuario(String nombre, String direccion, String telefono, LocalDate fechaNacimiento, Estado estado) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.estado = estado;
    }

    public Usuario(Usuario usuario) {
        this.nombre = usuario.nombre;
        this.direccion = usuario.direccion;
        this.telefono = usuario.telefono;
        this.fechaNacimiento = usuario.fechaNacimiento;
        this.estado = usuario.estado;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", super=" + super.toString() +
            ", estado='" + getEstado() + "'" +
            "}";
    }

    @Override
    public String toData() {
        return
            getId() + "…" +
            super.toData() +
            Usuario.getStringFromEstado((Estado)this.estado) + "•";
    }

    //funccions------------------------------------------------------

    public void cambiarDatos(String nombre, String direccion, String telefono, LocalDate fechaNacimiento, Estado estado) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.estado = estado;
    }

    public static Estado getEstadoFromString(String estado) {
        if(estado.equals("NADA")) return Estado.NADA;
        else if (estado.equals("ACTIVO")) return Estado.ACTIVO;
        else if (estado.equals("BAJA")) return Estado.BAJA;
        else if (estado.equals("SUSPENDIDO")) return Estado.SUSPENDIDO;
        else if (estado.equals("SANCIONADO")) return Estado.SANCIONADO;
        else {
            Debug.println("Estado introducido no existe");
            return Estado.NADA;
        }
    }

    public static String getStringFromEstado(Estado estado) {
        if(estado == Estado.NADA) return "NADA";
        else if(estado == Estado.ACTIVO) return "ACTIVO";
        else if(estado == Estado.BAJA) return "BAJA";
        else if(estado == Estado.SUSPENDIDO) return "SUSPENDIDO";
        else if(estado == Estado.SANCIONADO) return "SANCIONADO";
        else return "NADA";
    }


    //getters and setters----------------------------

    public long getId() {
        return id.getSerial();
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Estado getEstado() {
        return this.estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

}

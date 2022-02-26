package com.ticketSpace.ticketpro_app.CategoriasClienteFirebase;

public class ImgCatFirebaseElegida {

    String imagen;
    String nombre;
    String precio;
    String direccion;
    String fecha;

    public ImgCatFirebaseElegida() {
    }

    public ImgCatFirebaseElegida(String imagen, String nombre, String precio, String direccion, String fecha) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.precio = precio;
        this.direccion = direccion;
        this.fecha = fecha;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}

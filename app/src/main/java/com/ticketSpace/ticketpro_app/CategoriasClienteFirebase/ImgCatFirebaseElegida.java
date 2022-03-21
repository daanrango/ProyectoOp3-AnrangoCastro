package com.ticketSpace.ticketpro_app.CategoriasClienteFirebase;

public class ImgCatFirebaseElegida {

    String imagen;
    String nombre;
    String precio;
    String direccion;
    String fecha;

    //hasta aqui funciona bien

    String latitud;
    String longuitud;

    public ImgCatFirebaseElegida() {
    }

    public ImgCatFirebaseElegida(String imagen, String nombre, String precio, String direccion, String fecha, String latitud, String longuitud) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.precio = precio;
        this.direccion = direccion;
        this.fecha = fecha;
        this.latitud = latitud;
        this.longuitud = longuitud;
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

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLonguitud() {
        return longuitud;
    }

    public void setLonguitud(String longuitud) {
        this.longuitud = longuitud;
    }
}

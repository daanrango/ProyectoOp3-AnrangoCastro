package com.ticketSpace.ticketpro_app.Categorias.Cat_Firebase;

public class CategoriasF {
    String categoria;
    String imagen;

    public CategoriasF() {
    }

    public CategoriasF(String categoria, String imagen) {
        this.categoria = categoria;
        this.imagen = imagen;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}

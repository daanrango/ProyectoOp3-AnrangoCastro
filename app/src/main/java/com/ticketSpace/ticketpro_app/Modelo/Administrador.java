package com.ticketSpace.ticketpro_app.Modelo;

public class Administrador {

    String Boletos_Comprados,Fecha_Compra,Hora_Compra,
            Valor_Compra,evento,fechaEvento,id_user,imagenEvento,user_Compra;

    public Administrador() {

    }

    public Administrador(String boletos_Comprados, String fecha_Compra, String hora_Compra, String valor_Compra, String evento, String fechaEvento, String id_user, String imagenEvento, String user_Compra) {
        this.Boletos_Comprados = boletos_Comprados;
        this.Fecha_Compra = fecha_Compra;
        this.Hora_Compra = hora_Compra;
        this.Valor_Compra = valor_Compra;
        this.evento = evento;
        this.fechaEvento = fechaEvento;
        this.id_user = id_user;
        this.imagenEvento = imagenEvento;
        this.user_Compra = user_Compra;
    }

    public String getBoletos_Comprados() {
        return Boletos_Comprados;
    }

    public void setBoletos_Comprados(String boletos_Comprados) {
        Boletos_Comprados = boletos_Comprados;
    }

    public String getFecha_Compra() {
        return Fecha_Compra;
    }

    public void setFecha_Compra(String fecha_Compra) {
        Fecha_Compra = fecha_Compra;
    }

    public String getHora_Compra() {
        return Hora_Compra;
    }

    public void setHora_Compra(String hora_Compra) {
        Hora_Compra = hora_Compra;
    }

    public String getValor_Compra() {
        return Valor_Compra;
    }

    public void setValor_Compra(String valor_Compra) {
        Valor_Compra = valor_Compra;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(String fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getImagenEvento() {
        return imagenEvento;
    }

    public void setImagenEvento(String imagenEvento) {
        this.imagenEvento = imagenEvento;
    }

    public String getUser_Compra() {
        return user_Compra;
    }

    public void setUser_Compra(String user_Compra) {
        this.user_Compra = user_Compra;
    }
}

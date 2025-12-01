package com.example.myapplication.model;

public class Nota {
    private Integer idNota;
    private String titulo;
    private String texto;

    public Nota() {}
    public Nota(Integer id, String titulo, String texto) {
        this.idNota = id;
        this.titulo = titulo;
        this.texto = texto;
    }

    public Nota(String titulo, String texto) {
        this.titulo = titulo;
        this.texto = texto;
    }
    public Integer getIdNota() {
        return this.idNota;
    }
    public void setIdNota(Integer idNota) {
        this.idNota = idNota;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }
    public String getTexto() {
        return texto;
    }
}

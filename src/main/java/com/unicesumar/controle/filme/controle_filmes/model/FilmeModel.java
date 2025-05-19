package com.unicesumar.controle.filme.controle_filmes.model;

public class FilmeModel {

    private Long id;
    private String titulo;
    private String genero;
    private int anoLancamento;

    public FilmeModel() {
    }

    public FilmeModel(String titulo, String genero, int anoLancamento) {
        this.titulo = titulo;
        this.genero = genero;
        this.anoLancamento = anoLancamento;
    }

    public FilmeModel(Long id, String titulo, String genero, int anoLancamento) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.anoLancamento = anoLancamento;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return this.genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getAnoLancamento() {
        return this.anoLancamento;
    }

    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

}

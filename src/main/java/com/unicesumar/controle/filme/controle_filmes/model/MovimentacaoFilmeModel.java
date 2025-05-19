package com.unicesumar.controle.filme.controle_filmes.model;

import java.time.LocalDate;

public class MovimentacaoFilmeModel {

    private Long id;
    private FilmeModel filme;
    private UsuarioModel usuario;
    private boolean assistido;
    private LocalDate dataCadastro; // data que entrou na lista "para assistir"
    private LocalDate dataAssistido; // preenchido apenas se for assistido

    // outras informações se necessário
    public MovimentacaoFilmeModel() {
    }

    public MovimentacaoFilmeModel(FilmeModel filme, UsuarioModel usuario) {
        this.filme = filme;
        this.usuario = usuario;
        this.assistido = false;
        this.dataCadastro = LocalDate.now();
    }

    public MovimentacaoFilmeModel(Long id, FilmeModel filme, UsuarioModel usuario) {
        this.id = id;
        this.filme = filme;
        this.usuario = usuario;
        this.assistido = false;
        this.dataCadastro = LocalDate.now();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FilmeModel getFilme() {
        return this.filme;
    }

    public void setFilme(FilmeModel filme) {
        this.filme = filme;
    }

    public UsuarioModel getUsuario() {
        return this.usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public boolean isAssistido() {
        return this.assistido;
    }

    public void setAssistido(boolean assistido) {
        this.assistido = assistido;
    }

    public LocalDate getDataCadastro() {
        return this.dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public LocalDate getDataAssistido() {
        return this.dataAssistido;
    }

    public void setDataAssistido(LocalDate dataAssistido) {
        this.dataAssistido = dataAssistido;
    }

    public void marcarComoAssistido() {
        this.assistido = true;
        this.dataAssistido = LocalDate.now();
    }

    public void marcarComoNaoAssistido() {
        this.assistido = false;
        this.dataAssistido = null;
    }

}

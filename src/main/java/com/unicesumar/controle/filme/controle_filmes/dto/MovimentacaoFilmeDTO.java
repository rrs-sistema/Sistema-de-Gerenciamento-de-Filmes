package com.unicesumar.controle.filme.controle_filmes.dto;

import java.time.LocalDate;

public class MovimentacaoFilmeDTO {

    private Long id;
    private Long idFilme;
    private Long idUsuario;
    private String nomeFilme;
    private String nomeUsuario;
    private boolean assistido;
    private LocalDate dataCadastro;
    private LocalDate dataAssistido;

    public MovimentacaoFilmeDTO() {
    }

    public MovimentacaoFilmeDTO(Long id, Long idFilme, Long idUsuario, String nomeFilme, String nomeUsuario,
            boolean assistido, LocalDate dataCadastro, LocalDate dataAssistido) {
        this.id = id;
        this.idFilme = idFilme;
        this.idUsuario = idUsuario;
        this.nomeFilme = nomeFilme;
        this.nomeUsuario = nomeUsuario;
        this.assistido = assistido;
        this.dataCadastro = dataCadastro;
        this.dataAssistido = dataAssistido;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdFilme() {
        return this.idFilme;
    }

    public void setIdFilme(Long idFilme) {
        this.idFilme = idFilme;
    }

    public Long getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeFilme() {
        return this.nomeFilme;
    }

    public void setNomeFilme(String nomeFilme) {
        this.nomeFilme = nomeFilme;
    }

    public String getNomeUsuario() {
        return this.nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public boolean isAssistido() {
        return this.assistido;
    }

    public boolean getAssistido() {
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

}

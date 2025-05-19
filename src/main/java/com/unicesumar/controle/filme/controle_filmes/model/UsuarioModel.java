package com.unicesumar.controle.filme.controle_filmes.model;

public class UsuarioModel {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String telefone;

    public UsuarioModel() {
    }

    public UsuarioModel(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public Boolean validaSenha(String senha, String confirmaSenha) {
        if (senha.equals(confirmaSenha) == false) {
            return false;
        } else {
            return true;
        }
    }

    public UsuarioModel(Long id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public UsuarioModel(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public UsuarioModel(String nome, String email, String senha, String telefone) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

}

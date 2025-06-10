package com.unicesumar.controle.filme.controle_filmes.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_movimento")
public class MovimentacaoFilmeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "filme_id")
    private FilmeModel filme;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioModel usuario;

    private boolean assistido;
    private LocalDateTime dataCadastro; // data que entrou na lista "para assistir"
    private LocalDateTime dataAssistido; // preenchido apenas se for assistido

    public void marcarComoAssistido() {
        this.assistido = true;
        this.dataAssistido = LocalDateTime.now();
    }

    public void marcarComoNaoAssistido() {
        this.assistido = false;
        this.dataAssistido = null;
    }

}

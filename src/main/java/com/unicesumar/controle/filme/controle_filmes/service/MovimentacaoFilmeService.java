package com.unicesumar.controle.filme.controle_filmes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unicesumar.controle.filme.controle_filmes.model.FilmeModel;
import com.unicesumar.controle.filme.controle_filmes.model.MovimentacaoFilmeModel;
import com.unicesumar.controle.filme.controle_filmes.model.UsuarioModel;
import com.unicesumar.controle.filme.controle_filmes.repository.FilmeRepository;
import com.unicesumar.controle.filme.controle_filmes.repository.MovimentacaoFilmeRepository;
import com.unicesumar.controle.filme.controle_filmes.repository.UsuarioRepository;

import java.time.LocalDateTime;

@Service
public class MovimentacaoFilmeService {

    @Autowired
    private MovimentacaoFilmeRepository movimentacaoRepository;

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean salvar(Long filmeId, Long usuarioId) {
        FilmeModel filme = filmeRepository.findById(filmeId)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado."));

        UsuarioModel usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        MovimentacaoFilmeModel mov = new MovimentacaoFilmeModel();
        mov.setFilme(filme);
        mov.setUsuario(usuario);
        mov.setAssistido(false);
        mov.setDataCadastro(LocalDateTime.now());
        movimentacaoRepository.save(mov);
        return true;
    }

    public void alterarStatus(Long id) {
        MovimentacaoFilmeModel mov = movimentacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimentação não encontrada."));

        if (mov.isAssistido()) {
            mov.setAssistido(false);
            mov.setDataAssistido(null);
        } else {
            mov.setAssistido(true);
            mov.setDataAssistido(LocalDateTime.now());
        }

        movimentacaoRepository.save(mov);
    }

    public void remover(Long id) {
        MovimentacaoFilmeModel mov = movimentacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimentação não encontrada."));

        if (mov.isAssistido()) {
            throw new RuntimeException("Não é possível remover filmes já assistidos.");
        }

        movimentacaoRepository.delete(mov);
    }

    public List<MovimentacaoFilmeModel> listarPorUsuario(Long usuarioId) {
        return movimentacaoRepository.findByUsuarioId(usuarioId);
    }
}

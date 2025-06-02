package com.unicesumar.controle.filme.controle_filmes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unicesumar.controle.filme.controle_filmes.model.MovimentacaoFilmeModel;

@Repository
public interface MovimentacaoFilmeRepository extends JpaRepository<MovimentacaoFilmeModel, Long> {
    List<MovimentacaoFilmeModel> findByUsuarioId(Long usuarioId);
}

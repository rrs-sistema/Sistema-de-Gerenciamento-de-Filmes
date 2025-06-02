package com.unicesumar.controle.filme.controle_filmes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unicesumar.controle.filme.controle_filmes.model.FilmeModel;

@Repository
public interface FilmeRepository extends JpaRepository<FilmeModel, Long> {
    Optional<FilmeModel> findByTitulo(String titulo);
}

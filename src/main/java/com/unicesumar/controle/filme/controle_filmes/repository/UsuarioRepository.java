package com.unicesumar.controle.filme.controle_filmes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unicesumar.controle.filme.controle_filmes.model.UsuarioModel;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
    Optional<UsuarioModel> findByEmail(String email);

    Optional<UsuarioModel> findByEmailAndSenha(String email, String senha);
}

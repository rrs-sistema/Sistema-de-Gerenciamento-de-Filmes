package com.unicesumar.controle.filme.controle_filmes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unicesumar.controle.filme.controle_filmes.model.UsuarioModel;
import com.unicesumar.controle.filme.controle_filmes.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean save(UsuarioModel usuario) {
        if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
            throw new RuntimeException("Nome não pode ser vazio.");
        }
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            throw new RuntimeException("Email não pode ser vazio.");
        }
        if (!usuario.getEmail().contains("@")) {
            throw new RuntimeException("Email inválido.");
        }
        if (usuario.getSenha() == null || usuario.getSenha().length() < 6) {
            throw new RuntimeException("Senha deve ter pelo menos 6 caracteres.");
        }

        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado.");
        }
        usuarioRepository.save(usuario);
        return true;
    }

    public Optional<UsuarioModel> login(String email, String senha) {
        return usuarioRepository.findByEmailAndSenha(email, senha);
    }

    public Optional<UsuarioModel> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public List<UsuarioModel> findAll() {
        return usuarioRepository.findAll();
    }
}

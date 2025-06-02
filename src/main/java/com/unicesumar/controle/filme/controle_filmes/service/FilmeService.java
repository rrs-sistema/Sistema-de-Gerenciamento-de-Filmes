package com.unicesumar.controle.filme.controle_filmes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unicesumar.controle.filme.controle_filmes.model.FilmeModel;
import com.unicesumar.controle.filme.controle_filmes.repository.FilmeRepository;

@Service
public class FilmeService {

    @Autowired
    private FilmeRepository filmeRepository;

    public boolean save(FilmeModel filme) {
        if (filme.getTitulo() == null || filme.getTitulo().isEmpty()) {
            throw new RuntimeException("Título não pode ser vazio.");
        }

        Optional<FilmeModel> existe = filmeRepository.findByTitulo(filme.getTitulo());
        if (existe.isPresent()) {
            throw new RuntimeException("Filme já cadastrado com este título.");
        }

        filmeRepository.save(filme);
        return true;
    }

    public List<FilmeModel> findAll() {
        return filmeRepository.findAll();
    }

    public Optional<FilmeModel> findById(Long id) {
        return filmeRepository.findById(id);
    }

    public void delete(Long id) {
        filmeRepository.deleteById(id);
    }
}

package com.unicesumar.controle.filme.controle_filmes.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.unicesumar.controle.filme.controle_filmes.dto.FilmeDTO;
import com.unicesumar.controle.filme.controle_filmes.model.FilmeModel;

import jakarta.servlet.http.HttpSession;

@Service
@SuppressWarnings("unchecked")
public class FilmeService {

    public Boolean save(String titulo, String genero, int anoLancamento, Model model, HttpSession session) {
        // Recupera a lista de usuários da sessão

        List<FilmeModel> filmes = (List<FilmeModel>) session.getAttribute("filmes");
        if (filmes == null) {
            filmes = new ArrayList<>();
        }

        if (titulo == null || titulo.isEmpty()) {
            throw new RuntimeException("Por favor informar o título do filme!");
        }
        var tituloExiste = filmes.stream().filter(filme -> filme.getTitulo().equals(titulo)).findFirst();
        if (tituloExiste.isPresent()) {
            model.addAttribute("msg", "Ops! Já existe um filme com esse título cadastrado!");
            return false;
        }
        long id = 1;
        if (filmes != null && filmes.size() > 0) {
            id++;
        }

        // Adiciona o novo filme à lista de filmes
        FilmeModel Filme = new FilmeModel(id, titulo, genero, anoLancamento);
        filmes.add(Filme);

        // Armazena a lista de filmes atualizada na sessão
        session.setAttribute("filmes", filmes);

        return true;
    }

    public FilmeModel update(FilmeModel Filme, HttpSession session) {
        // Recupera a lista de filmes da sessão
        List<FilmeModel> filmes = (List<FilmeModel>) session.getAttribute("filmes");

        // Se a lista de filmes não estiver na sessão, cria uma nova
        if (filmes == null) {
            filmes = new ArrayList<>();
            session.setAttribute("filmes", filmes); // Salva a lista na sessão se não existir
        }

        // Encontra a Filme existente na lista
        var filmeExistente = filmes.stream()
                .filter(taf -> taf.getId().equals(Filme.getId()))
                .findFirst()
                .orElse(null);

        // Se a Filme for encontrada, atualiza ela
        if (filmeExistente != null) {
            filmes.remove(filmeExistente); // Remove a Filme antiga
            filmes.add(Filme); // Adiciona a Filme atualizada
        } else {
            throw new RuntimeException("Filme não encontrado.");
        }

        // Atualiza a lista de filmes na sessão
        session.setAttribute("filmes", filmes);

        return Filme;
    }

    public FilmeModel updateStatus(Long id, HttpSession session) {
        FilmeModel Filme = null;
        // Recupera a lista de filmes da sessão
        List<FilmeModel> filmes = (List<FilmeModel>) session.getAttribute("filmes");

        // Se a lista de filmes não estiver na sessão, cria uma nova
        if (filmes == null) {
            filmes = new ArrayList<>();
            session.setAttribute("filmes", filmes); // Salva a lista na sessão se não existir
        }

        // Encontra a Filme existente na lista
        Filme = filmes.stream()
                .filter(taf -> taf.getId().equals(
                        id))
                .findFirst()
                .orElse(null);

        // Se a Filme for encontrada, atualiza ela
        if (Filme != null) {
            filmes.remove(Filme); // Remove a Filme antiga
            filmes.add(Filme); // Adiciona a Filme atualizada
        } else {
            throw new RuntimeException("Filme não encontrado.");
        }

        // Atualiza a lista de filmes na sessão
        session.setAttribute("filmes", filmes);

        return Filme;
    }

    public Optional<FilmeModel> findById(Long id, HttpSession session) {
        List<FilmeModel> filmes = (List<FilmeModel>) session.getAttribute("filmes");
        return filmes.stream().filter(usuario -> usuario.getId().equals(id)).findFirst();
    };

    public void findAll(Model model, HttpSession session) {
        List<FilmeModel> filmes = (List<FilmeModel>) session.getAttribute("filmes");

        List<FilmeDTO> listFilmeDTO = new ArrayList<>();

        if (filmes != null) {
            for (FilmeModel Filme : filmes) {
                FilmeDTO filmeDTO = new FilmeDTO(
                        Filme.getId(),
                        Filme.getTitulo(),
                        Filme.getGenero(),
                        Filme.getAnoLancamento());
                listFilmeDTO.add(filmeDTO);
            }
        }

        model.addAttribute("filmes", listFilmeDTO);
    }

}

package com.unicesumar.controle.filme.controle_filmes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unicesumar.controle.filme.controle_filmes.model.FilmeModel;
import com.unicesumar.controle.filme.controle_filmes.service.FilmeService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/filmes")
public class FilmeController {

    @Autowired
    private FilmeService filmeService;

    // Exibe a página de cadastro de filme
    @GetMapping("/cadastro")
    public String exibirFormularioCadastro(HttpSession session, Model model) {
        if (!usuarioAutenticado(session)) {
            return "redirect:/login";
        }

        model.addAttribute("filme", new FilmeModel());
        return "cadastro-filme"; // View: templates/cadastro-filme.html
    }

    // Processa o cadastro de um novo filme
    @PostMapping("/cadastro")
    public String cadastrarFilme(@ModelAttribute FilmeModel filme,
            HttpSession session, Model model) {
        if (!usuarioAutenticado(session)) {
            return "redirect:/login";
        }

        try {
            filmeService.save(filme);
            return "redirect:/filmes/lista";
        } catch (RuntimeException e) {
            model.addAttribute("msg", e.getMessage());
            model.addAttribute("filme", filme);
            return "cadastro-filme";
        }
    }

    // Lista todos os filmes
    @GetMapping("/lista")
    public String listarFilmes(HttpSession session, Model model) {
        if (!usuarioAutenticado(session)) {
            return "redirect:/login";
        }

        model.addAttribute("filmes", filmeService.findAll());
        return "lista-filme"; // View: templates/lista-filme.html
    }

    // (Opcional) Alternar status de algo relacionado ao filme
    @GetMapping("/alterar-status/{id}")
    public String alterarStatus(@PathVariable Long id, HttpSession session) {
        if (!usuarioAutenticado(session)) {
            return "redirect:/login";
        }

        // Se você futuramente adicionar um campo "ativo"/"assistido" no FilmeModel
        // então implemente o método updateStatus no service
        // filmeService.updateStatus(id);
        return "redirect:/filmes/lista";
    }

    // Utilitário: verifica se o usuário está logado
    private boolean usuarioAutenticado(HttpSession session) {
        return session.getAttribute("usuario") != null;
    }
}

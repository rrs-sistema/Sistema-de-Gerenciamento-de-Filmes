package com.unicesumar.controle.filme.controle_filmes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.unicesumar.controle.filme.controle_filmes.model.UsuarioModel;
import com.unicesumar.controle.filme.controle_filmes.service.FilmeService;

import jakarta.servlet.http.HttpSession;

@Controller
public class FilmeController {

    @Autowired
    FilmeService filmeService;

    // Exibe a página de cadastro
    @SuppressWarnings("unchecked")
    @GetMapping("/cadastro-filme")
    public String cadastro(HttpSession session, Model model) {
        UsuarioModel usuario = (UsuarioModel) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login"; // Redireciona para a página de login se o usuário não estiver autenticado
        }

        List<UsuarioModel> usuarios = (List<UsuarioModel>) session.getAttribute("usuarios");
        // Adiciona a lista de usuários no modelo para que ela seja acessada no
        model.addAttribute("usuarios", usuarios);

        return "cadastro-filme"; // Retorna a página de cadastro (sem a extensão .html)
    }

    // Processa o cadastro de um novo usuário
    @PostMapping("/cadastro-filme")
    public String cadastrar(@RequestParam String titulo, @RequestParam String genero, @RequestParam int anoLancamento,
            HttpSession session, Model model) {
        // String titulo, String genero, int anoLancamento,
        Boolean cadastrou = filmeService.save(titulo, genero, anoLancamento, model, session);
        if (!cadastrou) {
            return "cadastro-filme";
        }
        return "redirect:/lista-filme"; // Redireciona para a página de lista de filmes
    }

    @GetMapping("/lista-filme")
    public String pesquisar(HttpSession session, Model model) {
        Object usuario = session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login"; // Redireciona para a página de login se o usuário não estiver autenticado
        }
        filmeService.findAll(model, session);
        return "lista-filme";
    }

    @GetMapping("/alterar-status/{id}")
    public String alterarStatus(@PathVariable Long id, HttpSession session) {
        filmeService.updateStatus(id, session); // Salva a filme com o status alterado
        return "redirect:/lista-filme"; // Redireciona de volta para a lista de filmes
    }

}

package com.unicesumar.controle.filme.controle_filmes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.unicesumar.controle.filme.controle_filmes.model.UsuarioModel;
import com.unicesumar.controle.filme.controle_filmes.service.MovimentacaoFilmeService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private MovimentacaoFilmeService movimentacaoService;

    // Página inicial após login
    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        UsuarioModel usuario = (UsuarioModel) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("nome", usuario.getNome());

        // Busca lista de movimentações do usuário
        var movimentacoes = movimentacaoService.listarPorUsuario(usuario.getId());
        model.addAttribute("movimentacoes", movimentacoes);

        return "home"; // templates/home.html
    }

    // Logout do sistema
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("usuario"); // ou session.invalidate();
        return "redirect:/usuarios/login"; // Redireciona para tela de login
    }
}

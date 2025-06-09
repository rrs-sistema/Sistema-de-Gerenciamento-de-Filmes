package com.unicesumar.controle.filme.controle_filmes.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.unicesumar.controle.filme.controle_filmes.model.UsuarioModel;
import com.unicesumar.controle.filme.controle_filmes.service.MovimentacaoFilmeService;
import com.unicesumar.controle.filme.controle_filmes.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private MovimentacaoFilmeService movimentacaoService;

    @Autowired
    private UsuarioService usuarioService;

    // Página inicial após login
    @GetMapping("/home")
    public String home(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String email = userDetails.getUsername();

        // Buscar o UsuarioModel no banco
        Optional<UsuarioModel> usuario = usuarioService.findByEmail(email);

        if (usuario.isEmpty()) {
            // Em caso de falha (não deve ocorrer), redireciona para login
            return "redirect:/usuarios/login";
        }

        // Adiciona nome do usuário no model
        model.addAttribute("nome", usuario.get().getNome());

        // Busca lista de movimentações do usuário
        var movimentacoes = movimentacaoService.listarPorUsuario(usuario.get().getId());
        model.addAttribute("movimentacoes", movimentacoes);

        // Retorna a página home
        return "home";
    }

    // Logout do sistema
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("usuario"); // ou session.invalidate();
        return "redirect:/usuarios/login"; // Redireciona para tela de login
    }
}

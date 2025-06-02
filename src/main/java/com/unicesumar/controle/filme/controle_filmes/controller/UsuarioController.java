package com.unicesumar.controle.filme.controle_filmes.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.unicesumar.controle.filme.controle_filmes.model.UsuarioModel;
import com.unicesumar.controle.filme.controle_filmes.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Página de login
    @GetMapping("/login")
    public String exibirLogin(Model model) {
        model.addAttribute("usuario", new UsuarioModel());
        return "login";
    }

    // Valida as credenciais do usuário
    @PostMapping("/login")
    public String validarLogin(@RequestParam String email,
            @RequestParam String senha,
            HttpSession session,
            Model model) {
        Optional<UsuarioModel> usuarioOpt = usuarioService.login(email, senha);

        if (usuarioOpt.isPresent()) {
            session.setAttribute("usuario", usuarioOpt.get());
            return "redirect:/home";
        } else {
            model.addAttribute("msg", "Credenciais inválidas. Tente novamente.");
            return "login";
        }
    }

    // Página de cadastro
    @GetMapping("/cadastro")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("usuario", new UsuarioModel());
        return "cadastro-usuario";
    }

    // Processa o cadastro de novo usuário
    @PostMapping("/cadastro")
    public String cadastrarUsuario(@ModelAttribute UsuarioModel usuario,
            @RequestParam String confirmaSenha,
            Model model) {
        if (!usuario.validaSenha(usuario.getSenha(), confirmaSenha)) {
            model.addAttribute("msg", "As senhas não conferem.");
            return "cadastro-usuario";
        }

        try {
            usuarioService.save(usuario);
            return "redirect:/usuarios/login";
        } catch (RuntimeException e) {
            model.addAttribute("msg", e.getMessage());
            return "cadastro-usuario";
        }
    }

    // Lista de usuários cadastrados
    @GetMapping("/lista")
    public String listarUsuarios(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/usuarios/login";
        }

        model.addAttribute("usuarios", usuarioService.findAll());
        return "lista-usuario";
    }
}

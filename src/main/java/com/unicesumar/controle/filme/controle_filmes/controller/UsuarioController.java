package com.unicesumar.controle.filme.controle_filmes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.unicesumar.controle.filme.controle_filmes.model.UsuarioModel;
import com.unicesumar.controle.filme.controle_filmes.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Exibe a página de login
     */
    @GetMapping("/login")
    public String exibirLogin(@RequestParam(required = false) String error,
            @RequestParam(required = false) String logout,
            Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            // Usuário já logado → redireciona para /home
            return "redirect:/home";
        }

        if (error != null) {
            model.addAttribute("msg", "Credenciais inválidas. Tente novamente.");
        }
        if (logout != null) {
            model.addAttribute("msg", "Você saiu com sucesso.");
        }

        return "login";
    }

    /**
     * Exibe a página de cadastro de novo usuário
     */
    @GetMapping("/cadastro")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("usuario", new UsuarioModel());
        return "cadastro-usuario";
    }

    /**
     * Processa o cadastro de novo usuário
     */
    @PostMapping("/cadastro")
    public String cadastrarUsuario(@ModelAttribute UsuarioModel usuario,
            @RequestParam String confirmaSenha,
            Model model) {
        if (!usuario.validaSenha(usuario.getSenha(), confirmaSenha)) {
            model.addAttribute("msg", "As senhas não conferem.");
            return "cadastro-usuario";
        }

        try {
            // Define a role padrão
            if (usuario.getEmail().toLowerCase().startsWith("admin@") ||
                    usuario.getEmail().toLowerCase().startsWith("rrs.sistema@") ||
                    usuario.getEmail().toLowerCase().startsWith("jullia.acsa@")) {
                usuario.setRole("ADMIN");
            } else {
                usuario.setRole("USER");
            }

            // Criptografa a senha antes de salvar
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

            // Salva o usuário no banco de dados
            usuarioService.save(usuario);

            // Redireciona para a tela de login
            return "redirect:/usuarios/login";
        } catch (RuntimeException e) {
            model.addAttribute("msg", e.getMessage());
            return "cadastro-usuario";
        }
    }

    /**
     * Lista todos os usuários cadastrados
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/lista")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.findAll());
        return "lista-usuario";
    }
}

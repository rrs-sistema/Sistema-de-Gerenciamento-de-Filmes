package com.unicesumar.controle.filme.controle_filmes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.unicesumar.controle.filme.controle_filmes.model.UsuarioModel;
import com.unicesumar.controle.filme.controle_filmes.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    // Exibe a página de login
    @GetMapping("/login")
    public String login() {
        return "login"; // Retorna a página de login (sem a extensão .html)
    }

    // Trata a requisição POST do formulário de login
    @PostMapping("/validar-credenciais")
    public String validarCredenciais(@RequestParam String email, @RequestParam String senha,
            Model model, HttpSession session) {
        // Verifica as credenciais do usuário
        Boolean logou = usuarioService.findByEmailAndSenha(email, senha, model, session);
        if (logou) {
            return "redirect:/lista-filme"; // Redireciona para a página inicial após login
        } else {
            return "login"; // Retorna para a página de login com mensagem de erro
        }
    }

    // Exibe a página de cadastro
    @GetMapping("/cadastro-usuario")
    public String cadastro() {
        return "cadastro-usuario"; // Retorna a página de cadastro (sem a extensão .html)
    }

    // Processa o cadastro de um novo usuário
    @PostMapping("/cadastro-usuario")
    public String cadastrar(@RequestParam String nome, @RequestParam String email, @RequestParam String senha,
            @RequestParam String confirmaSenha, Model model, HttpSession session) {

        UsuarioModel usuario = new UsuarioModel();
        if (!usuario.validaSenha(senha, confirmaSenha)) {
            model.addAttribute("msg", "As senhas não conferem!");
            return "cadastro-usuario"; // Retorna para a página de login com mensagem de erro
        }
        // Cria um novo usuário com os dados fornecidos
        Boolean cadastrou = usuarioService.save(nome, email, senha, model, session);

        if (!cadastrou) {
            return "cadastro-usuario";
        }
        // Redireciona para a página home após o cadastro
        return "redirect:/lista-usuario";
    }

    @GetMapping("/lista-usuario")
    public String pesquisar(HttpSession session, Model model) {
        Object usuario = session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login"; // Redireciona para a página de login se o usuário não estiver autenticado
        }
        usuarioService.findAll(model, session);
        return "lista-usuario";
    }
}

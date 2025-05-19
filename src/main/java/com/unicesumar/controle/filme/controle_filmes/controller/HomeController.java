package com.unicesumar.controle.filme.controle_filmes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.unicesumar.controle.filme.controle_filmes.dto.MovimentacaoFilmeDTO;
import com.unicesumar.controle.filme.controle_filmes.model.UsuarioModel;
import com.unicesumar.controle.filme.controle_filmes.service.MovimentacaoFilmeService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private MovimentacaoFilmeService movimentacaoService;

    // Exibe a página home após o login
    @SuppressWarnings("unchecked")
    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        // Verifica se o usuário está autenticado na sessão
        Object objectSession = session.getAttribute("usuario");
        if (objectSession != null) {
            UsuarioModel usuario = (UsuarioModel) objectSession;
            model.addAttribute("nome", usuario.getNome());
            session.setAttribute("usuario", usuario);

            movimentacaoService.listarTodos(model, session);
            List<MovimentacaoFilmeDTO> movimentacoes = (List<MovimentacaoFilmeDTO>) session
                    .getAttribute("movimentacoes");
            model.addAttribute(MovimentacaoFilmeService.SESSAO_MOVIMENTACOES, movimentacoes);
            return "home"; // Retorna a página home (home.html)
        }

        // Se não houver sessão, redireciona para login
        return "redirect:/login"; // Redireciona para a tela de login caso o usuário não esteja logado
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // session.invalidate(); // Invalida a sessão
        session.removeAttribute("usuario"); // Remove o usuário da sessão
        return "redirect:/index"; // Redireciona para a tela de login
    }
}

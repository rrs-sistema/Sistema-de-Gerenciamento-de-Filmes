package com.unicesumar.controle.filme.controle_filmes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.unicesumar.controle.filme.controle_filmes.dto.MovimentacaoFilmeDTO;
import com.unicesumar.controle.filme.controle_filmes.model.FilmeModel;
import com.unicesumar.controle.filme.controle_filmes.model.UsuarioModel;
import com.unicesumar.controle.filme.controle_filmes.service.MovimentacaoFilmeService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MovimentacaoFilmeController {

    @Autowired
    private MovimentacaoFilmeService movimentacaoService;

    // Exibe o formulário para adicionar um filme à lista "para assistir"
    @SuppressWarnings("unchecked")
    @GetMapping("/cadastro-filme-usuario")
    public String cadastro(HttpSession session, Model model) {
        UsuarioModel usuario = (UsuarioModel) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }

        List<UsuarioModel> usuarios = (List<UsuarioModel>) session.getAttribute("usuarios");
        List<FilmeModel> filmes = (List<FilmeModel>) session.getAttribute("filmes");

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("filmes", filmes);

        return "cadastro-filme-usuario";
    }

    // Processa o cadastro de um filme para assistir
    @PostMapping("/cadastro-filme-usuario")
    public String cadastrarFilmeParaUsuario(@RequestParam Long filmeId,
            @RequestParam Long usuarioId,
            HttpSession session,
            Model model) {
        boolean sucesso = movimentacaoService.salvar(filmeId, usuarioId, session, model);
        if (!sucesso) {
            return "cadastro-filme-usuario";
        }
        return "redirect:/lista-filmes-usuario";
    }

    // Lista os filmes assistidos e não assistidos
    @SuppressWarnings("unchecked")
    @GetMapping("/lista-filmes-usuario")
    public String listarFilmesDoUsuario(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }
        movimentacaoService.listarTodos(model, session);
        List<MovimentacaoFilmeDTO> movimentacoes = (List<MovimentacaoFilmeDTO>) session.getAttribute("movimentacoes");
        model.addAttribute(MovimentacaoFilmeService.SESSAO_MOVIMENTACOES, movimentacoes);
        return "home";
    }

    // Altera o status de assistido para não assistido ou vice-versa
    @GetMapping("/alterar-status-filme/{id}")
    public String alterarStatusFilme(@PathVariable Long id, HttpSession session) {
        movimentacaoService.alterarStatus(id, session);
        return "redirect:/lista-filmes-usuario";
    }

    // Remove filme da lista "para assistir"
    @GetMapping("/remover-filme/{id}")
    public String removerFilme(@PathVariable Long id, HttpSession session, Model model) {
        movimentacaoService.remover(id, session, model);
        return "redirect:/lista-filmes-usuario";
    }
}

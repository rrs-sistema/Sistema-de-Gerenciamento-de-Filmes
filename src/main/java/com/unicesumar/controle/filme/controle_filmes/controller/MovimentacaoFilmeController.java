package com.unicesumar.controle.filme.controle_filmes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.unicesumar.controle.filme.controle_filmes.model.UsuarioModel;
import com.unicesumar.controle.filme.controle_filmes.service.FilmeService;
import com.unicesumar.controle.filme.controle_filmes.service.MovimentacaoFilmeService;
import com.unicesumar.controle.filme.controle_filmes.service.UsuarioService;

@Controller
@RequestMapping("/movimentacoes")
public class MovimentacaoFilmeController {

    @Autowired
    private MovimentacaoFilmeService movimentacaoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private FilmeService filmeService;

    // Exibe o formulário para adicionar filme ao usuário
    @GetMapping("/cadastro")
    public String exibirFormularioCadastro(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        UsuarioModel usuario = getUsuarioLogado(userDetails);

        model.addAttribute("filmeId", null);
        model.addAttribute("usuarioId", usuario.getId());
        model.addAttribute("filmes", filmeService.findAll());
        model.addAttribute("usuarios", usuarioService.findAll());

        return "cadastro-filme-usuario";
    }

    // Processa o cadastro de um filme para assistir
    @PostMapping("/cadastro")
    public String cadastrarFilmeParaUsuario(@RequestParam Long filmeId, @RequestParam Long usuarioId,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {
        UsuarioModel usuario = getUsuarioLogado(userDetails);

        try {
            movimentacaoService.salvar(filmeId, usuarioId);
            return "redirect:/movimentacoes/lista";
        } catch (RuntimeException e) {
            model.addAttribute("msg", e.getMessage());
            model.addAttribute("filmes", filmeService.findAll());
            model.addAttribute("usuarioId", usuario.getId());
            return "cadastro-filme-usuario";
        }
    }

    // Lista todos os filmes movimentados pelo usuário
    @GetMapping("/lista")
    public String listarFilmesDoUsuario(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        UsuarioModel usuario = getUsuarioLogado(userDetails);

        model.addAttribute("movimentacoes", movimentacaoService.listarPorUsuario(usuario.getId()));
        return "home"; // templates/home.html
    }

    // Alterna status assistido/não assistido
    @GetMapping("/alterar-status/{id}")
    public String alterarStatus(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        getUsuarioLogado(userDetails);

        movimentacaoService.alterarStatus(id);
        return "redirect:/movimentacoes/lista";
    }

    // Remove um filme da lista do usuário
    @GetMapping("/remover/{id}")
    public String removerFilme(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        getUsuarioLogado(userDetails);

        try {
            movimentacaoService.remover(id);
        } catch (RuntimeException e) {
            model.addAttribute("msg", e.getMessage());
        }

        return "redirect:/movimentacoes/lista";
    }

    // 🔒 Utilitário para obter usuário logado com segurança
    private UsuarioModel getUsuarioLogado(UserDetails userDetails) {
        String email = userDetails.getUsername();
        return usuarioService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + email));
    }
}

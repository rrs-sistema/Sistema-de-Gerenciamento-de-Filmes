package com.unicesumar.controle.filme.controle_filmes.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.unicesumar.controle.filme.controle_filmes.dto.MovimentacaoFilmeDTO;
import com.unicesumar.controle.filme.controle_filmes.model.FilmeModel;
import com.unicesumar.controle.filme.controle_filmes.model.MovimentacaoFilmeModel;
import com.unicesumar.controle.filme.controle_filmes.model.UsuarioModel;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;

@Service
@SuppressWarnings("unchecked")
public class MovimentacaoFilmeService {

    public static final String SESSAO_MOVIMENTACOES = "movimentacoes";

    public boolean salvar(Long filmeId, Long usuarioId, HttpSession session, Model model) {
        // Recuperar usuários e filmes da sessão
        List<UsuarioModel> usuarios = (List<UsuarioModel>) session.getAttribute("usuarios");
        List<FilmeModel> filmes = (List<FilmeModel>) session.getAttribute("filmes");

        Optional<UsuarioModel> usuario = usuarios.stream()
                .filter(u -> u.getId().equals(usuarioId))
                .findFirst();

        Optional<FilmeModel> filme = filmes.stream()
                .filter(f -> f.getId().equals(filmeId))
                .findFirst();

        if (!usuario.isPresent() || !filme.isPresent()) {
            model.addAttribute("msg", "Filme ou usuário não encontrado.");
            return false;
        }

        // Cria nova movimentação com dados de cadastro
        MovimentacaoFilmeModel mov = new MovimentacaoFilmeModel();
        mov.setFilme(filme.get());
        mov.setUsuario(usuario.get());
        mov.setAssistido(false);
        mov.setDataCadastro(LocalDate.now());
        mov.setDataAssistido(null);

        // Recupera ou inicializa a lista de movimentações
        List<MovimentacaoFilmeDTO> movimentacoes = (List<MovimentacaoFilmeDTO>) session
                .getAttribute(SESSAO_MOVIMENTACOES);
        if (movimentacoes == null) {
            movimentacoes = new ArrayList<>();
        }

        mov.setId(movimentacoes.size() > 0 ? movimentacoes.size() + 1L : 1L);

        MovimentacaoFilmeDTO dto = new MovimentacaoFilmeDTO();
        dto.setId(mov.getId());
        dto.setIdFilme(mov.getFilme().getId());
        dto.setIdUsuario(mov.getUsuario().getId());
        dto.setNomeFilme(mov.getFilme().getTitulo());
        dto.setNomeUsuario(mov.getUsuario().getNome());
        dto.setAssistido(mov.isAssistido());
        dto.setDataCadastro(mov.getDataCadastro());
        dto.setDataAssistido(mov.getDataAssistido());
        movimentacoes.add(dto);

        session.setAttribute(SESSAO_MOVIMENTACOES, movimentacoes);

        movimentacoes = (List<MovimentacaoFilmeDTO>) session.getAttribute(SESSAO_MOVIMENTACOES);
        if (movimentacoes == null) {
            movimentacoes = new ArrayList<>();
            session.setAttribute(SESSAO_MOVIMENTACOES, movimentacoes);
        }

        return true;
    }

    public void alterarStatus(Long id, HttpSession session) {
        List<MovimentacaoFilmeDTO> movimentacoes = (List<MovimentacaoFilmeDTO>) session
                .getAttribute(SESSAO_MOVIMENTACOES);
        if (movimentacoes == null) {
            throw new RuntimeException("Nenhuma movimentação encontrada.");
        }

        MovimentacaoFilmeDTO entity = movimentacoes.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Movimentação não encontrada."));

        if (entity.isAssistido()) {
            entity.setAssistido(false);
            entity.setDataAssistido(null);
        } else {
            entity.setAssistido(true);
            entity.setDataAssistido(LocalDate.now());
        }

        movimentacoes.removeIf(m -> m.getId().equals(id));
        movimentacoes.add(entity);

        session.setAttribute(SESSAO_MOVIMENTACOES, movimentacoes);
    }

    public void listarTodos(Model model, HttpSession session) {
        List<MovimentacaoFilmeDTO> movimentacoes = (List<MovimentacaoFilmeDTO>) session
                .getAttribute(SESSAO_MOVIMENTACOES);

        List<MovimentacaoFilmeDTO> assistidos = new ArrayList<>();
        List<MovimentacaoFilmeDTO> naoAssistidos = new ArrayList<>();

        if (movimentacoes != null) {
            for (MovimentacaoFilmeDTO mov : movimentacoes) {
                if (mov.isAssistido()) {
                    assistidos.add(mov);
                } else {
                    naoAssistidos.add(mov);
                }
            }
        }

        model.addAttribute("assistidos", assistidos);
        model.addAttribute("naoAssistidos", naoAssistidos);
    }

    public boolean remover(Long id, HttpSession session, Model model) {
        List<MovimentacaoFilmeDTO> movimentacoes = (List<MovimentacaoFilmeDTO>) session
                .getAttribute(SESSAO_MOVIMENTACOES);

        if (movimentacoes == null) {
            return false;
        }

        Optional<MovimentacaoFilmeDTO> movOpt = movimentacoes.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();

        if (movOpt.isPresent()) {
            MovimentacaoFilmeDTO mov = movOpt.get();
            if (mov.isAssistido()) {
                model.addAttribute("msg", "Não é possível remover filmes já assistidos.");
                return false;
            }
            movimentacoes.remove(mov);
            session.setAttribute(SESSAO_MOVIMENTACOES, movimentacoes);
            return true;
        }

        return false;
    }

    public Optional<MovimentacaoFilmeDTO> findById(Long id, HttpSession session) {
        List<MovimentacaoFilmeDTO> movimentacoes = (List<MovimentacaoFilmeDTO>) session
                .getAttribute(SESSAO_MOVIMENTACOES);

        if (movimentacoes == null)
            return Optional.empty();

        return movimentacoes.stream().filter(m -> m.getId().equals(id)).findFirst();
    }
}

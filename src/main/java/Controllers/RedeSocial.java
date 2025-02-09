package Controllers;

import Models.*;

import java.util.Collections;
import java.util.ArrayList;
import java.util.function.Predicate;

public class RedeSocial {
    private ArrayList<PerfilAvancado> _perfil;
    private ArrayList<PublicacaoAvancada> _publicacao;
    private ArrayList<PedidoDeAmizade> _pedidosDeAmizade;

    // Construtor Padrao
    public RedeSocial() {
        this._perfil = new ArrayList<PerfilAvancado>();
        this._publicacao = new ArrayList<PublicacaoAvancada>();
        this._pedidosDeAmizade = new ArrayList<PedidoDeAmizade>();
    }

    // Getters e Setters 
    public ArrayList<PerfilAvancado> getPerfil() {
        return _perfil;
    }

    public void setPerfil(ArrayList<PerfilAvancado> Perfil) {
        this._perfil = Perfil;
    }

    public ArrayList<PublicacaoAvancada> getPublicacao() {
        return _publicacao;
    }

    public void setPublicacao(ArrayList<PublicacaoAvancada> Publicacao) {
        this._publicacao = Publicacao;
    }

    public ArrayList<PedidoDeAmizade> getPedidosDeAmizade() {
        return _pedidosDeAmizade;
    }

    public void setPedidosDeAmizade(ArrayList<PedidoDeAmizade> pedidosDeAmizade) {
        this._pedidosDeAmizade = pedidosDeAmizade;
    }

    // Métodos de Gerenciamento de Perfis
    public void adicionarPerfil(PerfilAvancado perfilAvancado) {
        this._perfil.add(perfilAvancado);
    }

    private PerfilAvancado buscarPerfilPorParametro(Predicate<PerfilAvancado> criterio) {
        return this._perfil.stream()
                .filter(criterio)
                .findFirst()
                .orElse(null);
    }

    public PerfilAvancado buscarPerfilPorEmail(String email) {
        return buscarPerfilPorParametro(perfilAvancado -> perfilAvancado.getEmail().equals(email));
    }

    public PerfilAvancado buscarPerfilPorNome(String nome) {
        return buscarPerfilPorParametro(perfilAvancado -> perfilAvancado.getApelido().equals(nome));
    }

    public PerfilAvancado buscarPerfilPorId(int id) {
        return buscarPerfilPorParametro(perfilAvancado -> perfilAvancado.getId() == id);
    }

    public ArrayList<PerfilAvancado> listarTodosPerfils() {
        return new ArrayList<PerfilAvancado>(this._perfil);
    }

    public void ativarPerfil(PerfilAvancado perfilAvancado) {
        perfilAvancado.habilitarPerfil(perfilAvancado);
    }

    public void desativarPerfil(PerfilAvancado perfilAvancado) {
        perfilAvancado.desabilitarPerfil(perfilAvancado);
    }

    // Métodos de Gerenciamento de Publicações
    public void adicionarPublicacao(PublicacaoAvancada publicacao) {
        this._publicacao.add(publicacao);
    }

    public PublicacaoAvancada buscarPublicacaoPorId(int id) {
        for (PublicacaoAvancada publicacao : _publicacao) {
            if (publicacao.getId() == id) {
                return publicacao;
            }
        }
        return null;
    }

    public ArrayList<PublicacaoAvancada> listarPublicacoes(PerfilAvancado perfil) {
        if (perfil != null) {
            ArrayList<PublicacaoAvancada> publicacoesPerfil = new ArrayList<>();
            for (PublicacaoAvancada publicacao : _publicacao) {
                if (publicacao.getPerfilAssociado().equals(perfil)) {
                    publicacoesPerfil.add(publicacao);
                }
            }
            Collections.sort(publicacoesPerfil, (publicacaoA, publicacaoB) -> publicacaoA.getDataPublicacao()
                    .compareTo(publicacaoB.getDataPublicacao()));
            return publicacoesPerfil;
        } else {
            ArrayList<PublicacaoAvancada> publicacoesPerfil = new ArrayList<>();
            for (PublicacaoAvancada publicacao : _publicacao) {
                if(publicacao.getPerfilAssociado().getStatus()){
                    publicacoesPerfil.add(publicacao);
                }
            }
            Collections.sort(publicacoesPerfil, (publicacaoA, publicacaoB) -> publicacaoA.getDataPublicacao()
                    .compareTo(publicacaoB.getDataPublicacao()));
            return publicacoesPerfil;
        }
    }


    // Métodos de Gerenciamento de solicitações de amizade
    public void enviarSolicitacaoAmizade(PerfilAvancado perfilSolicitante, PerfilAvancado perfilSolicitado) {
        this._pedidosDeAmizade.add(new PedidoDeAmizade(perfilSolicitante, perfilSolicitado));
    }

    public void removerAmizade(PerfilAvancado perfilRemetente, PerfilAvancado perfilDestinatario) {
        perfilRemetente.removerAmigo(perfilDestinatario);
        perfilDestinatario.removerAmigo(perfilRemetente);
    }

    public void aceitarSolicitacaoAmizade(PerfilAvancado perfilSolicitante, PerfilAvancado perfilSolicitado) {
        this._pedidosDeAmizade.removeIf(pedido -> pedido.getPerfilSolicitante().equals(perfilSolicitante) && pedido.getPerfilSolicitado().equals(perfilSolicitado));
        perfilSolicitante.adicionarAmigo(perfilSolicitado);
        perfilSolicitado.adicionarAmigo(perfilSolicitante);
    }

    public ArrayList<PerfilAvancado> listarSolicitacoesAmizade(PerfilAvancado perfil) {
        ArrayList<PerfilAvancado> solicitacoes = new ArrayList<>();
        for (PedidoDeAmizade pedido : this._pedidosDeAmizade) {
            if (pedido.getPerfilSolicitado().equals(perfil)) {
                if(pedido.getPerfilSolicitante().getStatus()){
                    solicitacoes.add(pedido.getPerfilSolicitante());
                }
            }
        }
        return solicitacoes;
    }

    public void recusarSolicitacaoAmizade(PerfilAvancado perfilSolicitante, PerfilAvancado perfilSolicitado) {
        this._pedidosDeAmizade.removeIf(pedido -> pedido.getPerfilSolicitante().equals(perfilSolicitante) && pedido.getPerfilSolicitado().equals(perfilSolicitado));
    }

    // Métodos de Gerenciamento de Interaçôes
    public Boolean adicionarInteracao(PerfilAvancado perfil, PublicacaoAvancada publicacao) {
        if (publicacao.jaInteragiu(perfil)) {
            return true;
        } else {
            return false;
        }
    }
}
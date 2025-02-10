package Views;

import Controllers.RedeSocial;
import Exceptions.Emoji.EmojiNaoEncontradoException;
import Exceptions.Geral.EmailInvalidoException;
import Exceptions.Geral.ValorInvalidoException;
import Exceptions.Interacoes.InteracaoDuplicadaError;
import Exceptions.PedidoAmizade.VerificarPedidoAmizadeError;
import Exceptions.Perfil.PerfilInexistenteError;
import Exceptions.Perfil.PerfilJaExisteError;
import Exceptions.Perfil.PerfilNaoAutorizadoError;
import Exceptions.Perfil.PerfilNaoEncontradoError;
import Exceptions.Publicacao.PublicacaoNaoEncontradaError;
import Models.*;
import Enum.TipoInteracao;
import Utils.Utils;
import java.util.ArrayList;

public class App {
    static Utils io = new Utils();
    public void fazerLogin(RedeSocial redeSocial, PerfilAtualLogado perfilAtualLogado) {
        String email = obterEmailDoPerfil();
        if (email == null) return;

        try {
            PerfilAvancado perfil = redeSocial.buscarPerfilPorEmail(email);
            if (perfil == null) {
                throw new PerfilNaoEncontradoError("\n\t\rPerfil não encontrado!");
            }

            perfilAtualLogado.setPerfilAtual(perfil);
            io.mostrarMensagem("\n\t\rLogin realizado com sucesso! Perfil: " + perfil.getApelido());
        } catch (PerfilNaoEncontradoError e) {
            io.mostrarMensagem(e.getMessage());
        }
    }
    private String obterEmailDoPerfil() {
        try {
            String email = io.lerString("\n\t\rDigite o email do perfil: ");
            io.validarEmail(email);
            return email;
        } catch (EmailInvalidoException | ValorInvalidoException e) {
            io.mostrarMensagem(e.getMessage());
            return null;
        }
    }
    public EmojiConverter determinarEmoj() throws ValorInvalidoException {
        EmojiConverter emojiConverter = new EmojiConverter();
        String[] arrayDeEmoji = emojiConverter.lerEmoji();

        int escolhaUser = -1;

        while (true) {
            io.mostrarMensagem("\n\t\rEscolha um emoji para o perfil: ");
            io.pecorrerListaDeEmoji(arrayDeEmoji);

            try {
                String entrada = io.lerString("\n\t\rDigite o número correspondente ao emoji desejado: ");

                try {
                    escolhaUser = Integer.parseInt(entrada);

                    if (escolhaUser < 1 || escolhaUser > arrayDeEmoji.length) {
                        throw new EmojiNaoEncontradoException("\n\t\rNúmero de emoji inválido! Por favor, escolha um número válido.");
                    }

                    return new EmojiConverter(arrayDeEmoji[escolhaUser - 1]);

                } catch (NumberFormatException e) {
                    io.mostrarMensagem("\n\t\rEntrada inválida! Por favor, insira um número.");
                }

            } catch (EmojiNaoEncontradoException e) {
                io.mostrarMensagem(e.getMessage());
            }
        }
    }
    public void adicionarPerfil(RedeSocial redeSocial, PerfilAtualLogado perfilAtualLogado) {
        try {
            String apelido = io.lerString("\n\t\rDigite o apelido do perfil: ");
            String email = obterEmailDoPerfil();
            if (email == null) return;

            EmojiConverter emoji = determinarEmoj();
            if (emoji == null) return;

            PerfilAvancado perfil = io.validarPerfil(new PerfilAvancado(apelido, emoji, email),redeSocial);
            redeSocial.adicionarPerfil(perfil);
            perfilAtualLogado.setPerfilAtual(perfil);
            perfilAtualLogado.getPerfilAtual().setContadorId(perfilAtualLogado.getPerfilAtual().getContadorId() + 1);

            io.mostrarMensagem("\n\t\rPerfil adicionado com sucesso! " +
                    "Nome: " + perfil.getApelido() + " Email: " + perfil.getEmail() +
                    " Emoji: " + perfil.getEmoji() + " Id: " + perfil.getId());
            io.mostrarMensagem("\n\t\rPerfil logado com sucesso! " + perfil.getApelido());
        } catch (ValorInvalidoException | PerfilJaExisteError e) {
            io.mostrarMensagem(e.getMessage());
        }
    }
    public void buscarPerfil(RedeSocial redeSocial) {
        try {
            String tipoBusca = io.lerString("\n\t\rDigite o tipo de busca (apelido / email / id) do perfil: ").toLowerCase();

            if(tipoBusca.equals("apelido") || tipoBusca.equals("email") || tipoBusca.equals("id")) {
                String parametroBusca = io.lerString("\n\t\rDigite o " + tipoBusca + " de busca: ");

                try{
                    if(tipoBusca.equals("email")){
                        io.validarEmail(parametroBusca);
                    }
                    try{
                        PerfilAvancado perfilParaBuscar = buscarPerfilGenerico(redeSocial, tipoBusca, parametroBusca);
                        if(perfilParaBuscar == null) {
                            throw new PerfilInexistenteError("\n\t\rPerfil não existe!");
                        }
                        if (perfilParaBuscar.getStatus()) {
                            io.mostrarMensagem("\n\t\rPerfil encontrado: " +
                                    "Nome: " + perfilParaBuscar.getApelido() + " Email: " + perfilParaBuscar.getEmail() +
                                    " Emoji: " + perfilParaBuscar.getEmoji() + " Publicação(ões): " + redeSocial.listarPublicacoes(perfilParaBuscar).size() +
                                    " Amigo(s): " + perfilParaBuscar.listarAmigos().size());
                        }else {
                            throw new PerfilNaoEncontradoError("\n\t\rPerfil está desativado!");
                        }
                    }catch (PerfilInexistenteError | PerfilNaoEncontradoError e){
                        io.mostrarMensagem(e.getMessage());
                    }
                }catch (EmailInvalidoException e){
                    io.mostrarMensagem(e.getMessage());
                }

            }else{
                io.mostrarMensagem("Informe um tipo de busca válido!");
            }
        } catch (ValorInvalidoException e) {
            io.mostrarMensagem(e.getMessage());
        }
    }

    // OK
    public PerfilAvancado buscarPerfilGenerico(RedeSocial redeSocial, String tipoBusca, String parametroBusca) throws ValorInvalidoException{
        switch (tipoBusca) {
            case "email":
                return redeSocial.buscarPerfilPorEmail(parametroBusca);
            case "apelido":
                return redeSocial.buscarPerfilPorNome(parametroBusca);
            case "id":
                try {
                    int id = Integer.parseInt(parametroBusca);
                    return redeSocial.buscarPerfilPorId(id);
                } catch (NumberFormatException e) {
                    throw new ValorInvalidoException("ID inválido.");
                }
            default:
                throw new ValorInvalidoException("Tipo de busca inválido.");
        }
    }
    // OK
    public void listarTodosPerfis(RedeSocial redeSocial) {
        io.mostrarMensagem("\n\t\rTodos os perfis da rede...");
        io.pecorrerListaDePerfis(redeSocial);
    }
    // OK
    public void alterarStatusPerfilParaAtivo(RedeSocial redeSocial,PerfilAtualLogado perfilAtualLogado){
        PerfilAvancado perfilParaAlterar = perfilAtualLogado.getPerfilAtual();

        boolean confirmarAcao = io.obterConfirmacao("\n\t\rVocê deseja ativar o perfil? (S/N): ");

        if(confirmarAcao){
            redeSocial.ativarPerfil(perfilParaAlterar);
            io.mostrarMensagem("\n\t\rPerfil ativado com sucesso!");
        }else{
            io.mostrarMensagem("\n\t\rPerfil não foi ativado!");
        }
    }
    // OK
    public void alterarStatusPerfilParaInativo(RedeSocial redeSocial, PerfilAtualLogado perfilAtualLogado){
        PerfilAvancado perfilParaAlterar = perfilAtualLogado.getPerfilAtual();

        boolean confirmarAcao = io.obterConfirmacao("\n\t\rVocê deseja desativar o perfil? (S/N): ");

        if(confirmarAcao){
            redeSocial.desativarPerfil(perfilParaAlterar);
            io.mostrarMensagem("\n\t\rPerfil desativado com sucesso!");
        }else{
            io.mostrarMensagem("\n\t\rPerfil não foi desativado!");
        }
    }
    // OK
    public void adicionarPublicacao(RedeSocial redeSocial, PerfilAtualLogado perfilAtualLogado) {
        PerfilAvancado perfilParaAdicionarPublicacao = perfilAtualLogado.getPerfilAtual();

        try {
            if (io.verificarPerfil(perfilParaAdicionarPublicacao)) {
                String conteudo = io.lerString("\n\t\rDigite o conteúdo da publicação: ");
                PublicacaoAvancada publicacaoAvancada = new PublicacaoAvancada(conteudo, perfilParaAdicionarPublicacao, new ArrayList<Interacao>());
                perfilParaAdicionarPublicacao.adicionarPostagem(publicacaoAvancada);
                redeSocial.adicionarPublicacao(publicacaoAvancada);
                io.mostrarMensagem("\n\t\rPublicação adicionada com sucesso!");
            } else {
                throw new PerfilNaoAutorizadoError("\n\t\rVocê não tem autorização para adicionar publicações a esse perfil.");
            }

        } catch (PerfilNaoAutorizadoError | PerfilNaoEncontradoError | ValorInvalidoException e) {
            io.mostrarMensagem(e.getMessage());
        }
    }
    //OK
    public void listarPublicacoes(RedeSocial redeSocial, PerfilAtualLogado perfilAtualLogado) {
        boolean confirmarAcao = io.obterConfirmacao("\n\t\rDeseja ver as publicações do perfil logado? (S/N): ");

        try{
            if (confirmarAcao) {
                if (io.verificarPerfil(perfilAtualLogado.getPerfilAtual())) {
                    ArrayList<PublicacaoAvancada> publicacoes = redeSocial.listarPublicacoes(perfilAtualLogado.getPerfilAtual());
                    if (!publicacoes.isEmpty()) {
                        io.pecorrerListaDePublicacoes(publicacoes);
                    } else {
                        io.mostrarMensagem("\n\t\rPerfil não possui publicações!");
                    }
                }
            } else {
                io.mostrarMensagem("\t\rTodas as publicações da rede...");
                ArrayList<PublicacaoAvancada> publicacoes = redeSocial.listarPublicacoes(null);
                if (!publicacoes.isEmpty()) {
                    io.pecorrerListaDePublicacoes(publicacoes);
                } else {
                    io.mostrarMensagem("\n\t\rNenhuma publicação na rede!");
                }
            }
        }catch (PerfilNaoEncontradoError e){
            io.mostrarMensagem(e.getMessage());
        }
    }

    public void interagirComPublicacao(RedeSocial redeSocial, PerfilAtualLogado perfilAtualLogado) {
        io.mostrarMensagem("\t\rTodas as publicações da rede...");
        ArrayList<PublicacaoAvancada> publicacoes = redeSocial.listarPublicacoes(null);
        io.pecorrerListaDePublicacoes(publicacoes);

        if(publicacoes.size() == 0){
            io.limparTerminal();
            return;
        }else{
            io.limparBuffer();
        }

        int idPublicacao = -1;
        boolean loop = true;

        while (loop) {
            try {
                idPublicacao = io.lerInteiro("\n\t\rDigite o ID da publicação que deseja interagir: ");
                int aux = 0;
                for(Publicacao publicacao : publicacoes) {
                    if(publicacao.getId() == idPublicacao) {
                        loop = false;
                        break;
                    }else{
                        aux++;
                    }
                }

                if(aux == publicacoes.size()) {
                    break;
                }

            } catch (ValorInvalidoException e) {
                io.mostrarMensagem("\n\t\rEntrada inválida! Por favor, insira um número válido para o ID da publicação.");
            }
        }

        PublicacaoAvancada publicacao = redeSocial.buscarPublicacaoPorId(idPublicacao);

        try {
            if (publicacao != null) {
                Boolean jaInteragiu = redeSocial.adicionarInteracao(perfilAtualLogado.getPerfilAtual(), publicacao);
                if (!jaInteragiu) {
                    String respostaInteracao = "";
                    while (true) {
                        respostaInteracao = io.lerString("\n\t\rDeseja: CURTIR, NAOCURTIR, SURPRESA ou RISO ?").toUpperCase();

                        if (respostaInteracao.isEmpty() || !respostaInteracao.matches("CURTIR|NAOCURTIR|SURPRESA|RISO")) {
                            io.mostrarMensagem("\n\t\rEntrada inválida! Por favor, insira uma opção válida (CURTIR, NAOCURTIR, SURPRESA ou RISO).");
                        } else {
                            break;
                        }
                    }

                    TipoInteracao tipoInteracao = TipoInteracao.valueOf(respostaInteracao);
                    Interacao interacao = new Interacao(tipoInteracao, perfilAtualLogado.getPerfilAtual());
                    publicacao.adicionarInteracao(interacao);
                    io.mostrarMensagem("\n\t\rInteragiu com a publicação com sucesso!");
                } else {
                    throw new InteracaoDuplicadaError("\n\t\rVocê já interagiu com essa publicação!");
                }
            } else {
                throw new PublicacaoNaoEncontradaError("\n\t\rPublicação não encontrada!");
            }
        } catch (InteracaoDuplicadaError | PublicacaoNaoEncontradaError | ValorInvalidoException e) {
            io.mostrarMensagem(e.getMessage());
        }
    }

    public void enviarPedidoDeAmizade(RedeSocial redeSocial, PerfilAtualLogado perfilAtualLogado) {
        try {
            String nome = io.lerString("\n\t\rDigite o apelido do perfil que deseja adicionar: ");
            PerfilAvancado perfilAvancado = redeSocial.buscarPerfilPorNome(nome);

            if (io.verificarPerfil(perfilAvancado) && perfilAvancado.getStatus()) {
                if(perfilAvancado != perfilAtualLogado.getPerfilAtual() ){
                    if(!redeSocial.listarSolicitacoesAmizade(perfilAvancado).contains(perfilAtualLogado.getPerfilAtual())
                            && !perfilAtualLogado.getPerfilAtual().listarAmigos().contains(perfilAvancado)) {
                        redeSocial.enviarSolicitacaoAmizade(perfilAtualLogado.getPerfilAtual(), perfilAvancado);
                        io.mostrarMensagem("\n\t\rPedido de amizade enviado com sucesso!");;
                    } else {
                        throw new VerificarPedidoAmizadeError("\n\t\rJá existe um pedido de amizade pendente para esse perfil ou já são amigos!");
                    }
                }else {
                    throw new VerificarPedidoAmizadeError("\n\t\rNão pode enviar uma solitação para o próprio perfil!");
                }
            } else {
                throw new PerfilNaoEncontradoError("\n\t\rPerfil não está registrado(inativo) ou não existe!");
            }

        } catch (PerfilNaoEncontradoError | VerificarPedidoAmizadeError | ValorInvalidoException e) {
            io.mostrarMensagem(e.getMessage());
        }
    }

    public void removerAmizade(RedeSocial redeSocial, PerfilAtualLogado perfilAtualLogado) {
        ArrayList<PerfilAvancado> amigos = perfilAtualLogado.getPerfilAtual().listarAmigos();

        try {
            if (amigos != null && !amigos.isEmpty()) {
                for (PerfilAvancado amigo : amigos) {
                    io.mostrarMensagem("\t\rAmigo: " + amigo.getApelido());
                }
                String nomeAmigo = io.lerString("\n\t\rDigite o apelido do perfil que deseja remover: ");
                PerfilAvancado perfilAmigo = redeSocial.buscarPerfilPorNome(nomeAmigo);

                if (perfilAmigo == null || !perfilAtualLogado.getPerfilAtual().listarAmigos().contains(perfilAmigo)) {
                    throw new PerfilNaoEncontradoError("\n\t\rPerfil não encontrado.");
                }

                redeSocial.removerAmizade(perfilAtualLogado.getPerfilAtual(), perfilAmigo);
                io.mostrarMensagem("\n\t\rAmizade removida com sucesso!");

            } else {
                io.mostrarMensagem("\n\t\rPerfil não possui amigos!");
            }

        } catch (PerfilNaoEncontradoError | ValorInvalidoException e) {
            io.mostrarMensagem(e.getMessage());
        }
    }

    public void solicitacaoDeAmizade(RedeSocial redeSocial, PerfilAtualLogado perfilAtualLogado) {
        ArrayList<PerfilAvancado> solicitacoes = redeSocial.listarSolicitacoesAmizade(perfilAtualLogado.getPerfilAtual());

        try {
            if (!solicitacoes.isEmpty()) {
                for (PerfilAvancado solicitacao : solicitacoes) {
                    io.mostrarMensagem("\t\rSolicitação de: " + solicitacao.getApelido());
                }
                String nomeSolicitante = io.lerString("\n\t\rDigite o apelido do perfil solicitante: ");
                Boolean solicitanteEncontrado = false;
                for (PerfilAvancado solicitacao : solicitacoes) {
                    if (solicitacao.getApelido().equals(nomeSolicitante)) {
                        solicitanteEncontrado = true;
                        break;
                    } else {
                        throw new PerfilNaoEncontradoError("\n\t\rPerfil solicitante não encontrado!");
                    }
                }
                if(solicitanteEncontrado) {
                    String respostaAceitar = io.lerString("\n\t\rDeseja aceitar a solicitação de amizade? (A/R): ").toUpperCase();
                    if (respostaAceitar.equals("A")) {
                        PerfilAvancado perfilSolicitante = redeSocial.buscarPerfilPorNome(nomeSolicitante);
                        if (io.verificarPerfil(perfilSolicitante)) {
                            redeSocial.aceitarSolicitacaoAmizade(perfilSolicitante, perfilAtualLogado.getPerfilAtual());
                            io.mostrarMensagem("\n\t\rSolicitação de amizade aceita com sucesso!");
                        }
                    } else {
                        PerfilAvancado perfilSolicitante = redeSocial.buscarPerfilPorNome(nomeSolicitante);
                        if (io.verificarPerfil(perfilSolicitante)) {
                            redeSocial.recusarSolicitacaoAmizade(perfilSolicitante, perfilAtualLogado.getPerfilAtual());
                            io.mostrarMensagem("\n\t\rSolicitação de amizade recusada com sucesso!");
                        }
                    }
                }
            } else {
                throw new VerificarPedidoAmizadeError("\n\t\rPerfil não possui solicitações de amizade!");
            }
        } catch (PerfilNaoEncontradoError | VerificarPedidoAmizadeError | ValorInvalidoException e) {
            io.mostrarMensagem(e.getMessage());
        }
    }

    public void listarAmigos(PerfilAtualLogado perfilAtualLogado) {
        ArrayList<PerfilAvancado> amigos = perfilAtualLogado.getPerfilAtual().listarAmigos();
        int aux = 0;

        if (amigos != null && !amigos.isEmpty()) {
            for (PerfilAvancado amigo : amigos) {
                if(amigo.getStatus()){
                    io.mostrarMensagem("\t\rAmigo: " + amigo.getApelido());
                }else{
                    aux++;
                }
            }

            if(aux == amigos.size()) {
                io.mostrarMensagem("\n\t\rPerfil não possui amigos!");
            }
        }

        else {
            io.mostrarMensagem("\n\t\rPerfil não possui amigos!");
        }
    }
}
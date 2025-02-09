import Controllers.RedeSocial;
import Models.PerfilAtualLogado;
import Services.DataProvider;
import Views.App;
import Utils.Utils;
import Exceptions.Geral.ValorInvalidoException;

public class Main {
    static Utils io = new Utils();

    public static void main(String[] args)  {
        DataProvider dataProvider = new DataProvider();
        RedeSocial antiSocialSocialClub = dataProvider.carregarSistema();
        PerfilAtualLogado perfilAtualLogado = new PerfilAtualLogado();
        App app = new App();

        while (true) {
            if (exibirMenuPrincipal(app, antiSocialSocialClub, perfilAtualLogado, dataProvider) == 0) {
                break;
            }
            if (perfilAtualLogado.getPerfilAtual() != null) {
                if(perfilAtualLogado.getPerfilAtual().getStatus()){
                    exibirMenuSecundarioPerfilAtivo(app, antiSocialSocialClub, perfilAtualLogado);
                }else{
                    exibirMenuSecundarioPerfilInativo(app, antiSocialSocialClub, perfilAtualLogado);
                }
            } else {
                io.mostrarMensagem("Nenhum perfil está logado. Por favor, faça o login.");
            }
        }
    }

    private static int exibirMenuPrincipal(App app, RedeSocial redeSocial, PerfilAtualLogado perfilAtualLogado, DataProvider dataProvider) {
        int escolha = -1;
        try{
            while (escolha != 0 && perfilAtualLogado.getPerfilAtual() == null) {
                io.mostrarMensagem("\n" +
                        "+--------------------------------------------+\n" +
                        "|    Bem-vindo ao Anti Social Social Club    |\n" +
                        "+--------------------------------------------+\n" +
                        "|    [1] - Fazer Login                       |\n" +
                        "|    [2] - Criar Perfil                      |\n" +
                        "|    [0] - Sair                              |\n" +
                        "+--------------------------------------------+\n");

                escolha = io.lerInteiro("\nDigite a opção desejada: ");

                boolean confirmarAcao = io.obterConfirmacao("\n\t\rVocê deseja confirmar essa opção (S/N): ");

                if(confirmarAcao) {
                    switch (escolha) {
                        case 1:
                            app.fazerLogin(redeSocial, perfilAtualLogado);
                            break;
                        case 2:
                            app.adicionarPerfil(redeSocial, perfilAtualLogado);
                            break;
                        case 0:
                            dataProvider.salvarSistema(redeSocial);
                            io.limparBuffer();
                            io.mostrarMensagem("\n\t\rObrigado por usar o Anti Social Social Club!");
                            break;
                        default:
                            io.mostrarMensagem("\n\t\rOpção inválida! Tente novamente...");
                            break;
                    }
                }
                if(escolha != 0 || !confirmarAcao){
                    io.limparBuffer();
                    io.limparTerminal();
                    escolha = -1;
                }else{
                    perfilAtualLogado.setPerfilAtual(null);
                }
            }
        }catch (ValorInvalidoException e){
            io.mostrarMensagem(e.getMessage());
            io.limparTerminal();
            exibirMenuPrincipal(app, redeSocial, perfilAtualLogado, dataProvider);
        }
        return escolha;
    }

    private static int exibirMenuSecundarioPerfilAtivo(App app, RedeSocial redeSocial, PerfilAtualLogado perfilAtualLogado) {
        int escolha = -1;
        try {
            while (escolha != 0 && perfilAtualLogado.getPerfilAtual() != null) {
                if (perfilAtualLogado.getPerfilAtual() != null) {
                    String nomePerfilLogado = perfilAtualLogado.getPerfilAtual().getApelido();
                    io.mostrarMensagem("\n" +
                            "+--------------------------------------------+\n" +
                            "|    Bem-vindo ao Anti Social Social Club    |\n" +
                            "|    Perfil logado: " + nomePerfilLogado + "           |\n" +
                            "+--------------------------------------------+\n" +
                            "\n" +
                            "+--------------------------------------------+\n" +
                            "|           GERÊNCIA DE PERFIS               |\n" +
                            "+--------------------------------------------+\n" +
                            "|    [1] - Buscar Perfil                     |\n" +
                            "|    [2] - Listar Todos os Perfis            |\n" +
                            "|    [3] - Desativar Perfil                  |\n" +
                            "+--------------------------------------------+\n" +
                            "\n" +
                            "+--------------------------------------------+\n" +
                            "|           GERÊNCIA DE PUBLICAÇÕES          |\n" +
                            "+--------------------------------------------+\n" +
                            "|    [4] - Adicionar Publicação              |\n" +
                            "|    [5] - Listar Publicações                |\n" +
                            "|    [6] - Interagir com Publicação          |\n" +
                            "+--------------------------------------------+\n" +
                            "\n" +
                            "+--------------------------------------------+\n" +
                            "|           GERÊNCIA DE AMIZADES             |\n" +
                            "+--------------------------------------------+\n" +
                            "|    [7] - Enviar Pedido de Amizade          |\n" +
                            "|    [8] - Remover Amizade                   |\n" +
                            "|    [9] - Pedidos de Amizade                |\n" +
                            "|    [10] - Listar Amigos                    |\n" +
                            "+--------------------------------------------+\n" +
                            "\n" +
                            "+--------------------------------------------+\n" +
                            "|    [0] - Logout                            |\n" +
                            "+--------------------------------------------+\n" +
                            "\n");

                    escolha = io.lerInteiro("Digite a opção desejada: ");

                    boolean confirmarAcao = io.obterConfirmacao("\n\t\rVocê deseja confirmar essa opção (S/N): ");

                    if (confirmarAcao) {
                        switch (escolha) {
                            case 1:
                                app.buscarPerfil(redeSocial);
                                break;
                            case 2:
                                app.listarTodosPerfis(redeSocial);
                                break;
                            case 3:
                                app.alterarStatusPerfilParaInativo(redeSocial, perfilAtualLogado);
                                if (!perfilAtualLogado.getPerfilAtual().getStatus()) {
                                    io.limparBuffer();
                                    io.limparTerminal();
                                    exibirMenuSecundarioPerfilInativo(app, redeSocial, perfilAtualLogado);
                                }else{
                                    io.limparBuffer();
                                    io.limparTerminal();
                                }
                                break;
                            case 4:
                                app.adicionarPublicacao(redeSocial, perfilAtualLogado);
                                break;
                            case 5:
                                app.listarPublicacoes(redeSocial, perfilAtualLogado);
                                break;
                            case 6:
                                app.interagirComPublicacao(redeSocial, perfilAtualLogado);
                                break;
                            case 7:
                                app.enviarPedidoDeAmizade(redeSocial, perfilAtualLogado);
                                break;
                            case 8:
                                app.removerAmizade(redeSocial, perfilAtualLogado);
                                break;
                            case 9:
                                app.solicitacaoDeAmizade(redeSocial, perfilAtualLogado);
                                break;
                            case 10:
                                app.listarAmigos(perfilAtualLogado);
                                break;
                            case 0:
                                io.limparBuffer();
                                break;
                            default:
                                io.mostrarMensagem("\n\t\rOpção inválida! Tente novamente...");
                                break;
                        }
                    }
                    if((escolha != 0 && escolha != 3) || !confirmarAcao){
                        io.limparBuffer();
                        io.limparTerminal();
                        escolha = -1;
                    }
                } else {
                    break;
                }
            }
        } catch (ValorInvalidoException e) {
            io.mostrarMensagem(e.getMessage());
            io.limparTerminal();
            exibirMenuSecundarioPerfilAtivo(app, redeSocial, perfilAtualLogado);
        }finally {
            perfilAtualLogado.setPerfilAtual(null);
        }
        return escolha;
    }

    private static int exibirMenuSecundarioPerfilInativo(App app, RedeSocial redeSocial, PerfilAtualLogado perfilAtualLogado) {
        int escolha = -1;
        try {
            while (escolha != 0) {
                if (perfilAtualLogado.getPerfilAtual() != null) {
                    String nomePerfilLogado = perfilAtualLogado.getPerfilAtual().getApelido();
                    io.mostrarMensagem("\n" +
                            "+--------------------------------------------+\n" +
                            "|    Bem-vindo ao Anti Social Social Club    |\n" +
                            "|    Perfil logado: " + nomePerfilLogado + "           |\n" +
                            "+--------------------------------------------+\n" +
                            "\n" +
                            "+--------------------------------------------+\n" +
                            "|           GERÊNCIA DE PERFIS               |\n" +
                            "+--------------------------------------------+\n" +
                            "|    [1] - Ativar Perfil                     |\n" +
                            "|    [0] - Logout                            |\n" +
                            "+--------------------------------------------+\n" +
                            "\n");

                    escolha = io.lerInteiro("Digite a opção desejada: ");

                    boolean confirmarAcao = io.obterConfirmacao("\n\t\rVocê deseja confirmar essa opção (S/N): ");

                    if (confirmarAcao) {
                        switch (escolha) {
                            case 1:
                                app.alterarStatusPerfilParaAtivo(redeSocial, perfilAtualLogado);
                                if (perfilAtualLogado.getPerfilAtual().getStatus()) {
                                    io.limparBuffer();
                                    io.limparTerminal();
                                    exibirMenuSecundarioPerfilAtivo(app, redeSocial, perfilAtualLogado);
                                }else {
                                    io.limparBuffer();
                                    io.limparTerminal();
                                }
                                break;
                            case 0:
                                io.limparBuffer();
                                break;
                            default:
                                io.mostrarMensagem("\n\t\rOpção inválida! Tente novamente...");
                                break;
                        }
                    }
                    if(!confirmarAcao){
                        io.limparBuffer();
                        io.limparTerminal();
                        escolha = -1;
                    }
                } else {
                    break;
                }
            }
        } catch (ValorInvalidoException e) {
            io.mostrarMensagem(e.getMessage());
            io.limparBuffer();
            io.limparTerminal();
            exibirMenuSecundarioPerfilInativo(app, redeSocial, perfilAtualLogado);
        } finally {
            perfilAtualLogado.setPerfilAtual(null);
        }
        return escolha;
    }
}

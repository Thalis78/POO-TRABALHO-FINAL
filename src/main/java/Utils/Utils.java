package Utils;

import Controllers.RedeSocial;
import Exceptions.Geral.EmailInvalidoException;
import Exceptions.Geral.ValorInvalidoException;
import Exceptions.Perfil.PerfilJaExisteError;
import Exceptions.Perfil.PerfilNaoEncontradoError;
import Models.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private static Scanner scanner = new Scanner(System.in, "UTF-8").useDelimiter("\n");

    public Utils() {
    }

    public int lerInteiro(String mensagem) throws ValorInvalidoException {
        System.out.print(mensagem);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new ValorInvalidoException("Entrada inválida! Por favor, insira um número válido.");
        }
    }

    public String lerString(String mensagem) throws ValorInvalidoException {
        System.out.println(mensagem);
        String texto = scanner.next().trim();
        if (texto.isEmpty()) {
            throw new ValorInvalidoException("O campo não pode ser vazio! Por favor, insira um valor.");
        }
        return texto;
    }

    public boolean obterConfirmacao(String mensagem) {
        try {
            String resposta = lerString(mensagem).toUpperCase();
            return resposta.equals("S");
        } catch (ValorInvalidoException e) {
            mostrarMensagem(e.getMessage());
        }
        return false;
    }

    public boolean verificarPerfil(Perfil perfil) throws PerfilNaoEncontradoError {
        if (perfil != null) {
            return true;
        } else {
            throw new PerfilNaoEncontradoError("Perfil não encontrado!");
        }
    }

    public void mostrarMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public void limparBuffer() {
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }

    public void limparTerminal() {
        System.out.println("Pressione ENTER para voltar ao Menu...");
        limparBuffer();
        mostrarMensagem("\n".repeat(15));
    }

    public void validarEmail(String email) throws EmailInvalidoException{
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            throw new EmailInvalidoException("O e-mail informado não é válido!");
        }
    }

    public PerfilAvancado validarPerfil(PerfilAvancado perfil,RedeSocial redeSocial) throws PerfilJaExisteError {
        for(PerfilAvancado perfilAvancado : redeSocial.getPerfil()){
            if(perfil.getApelido().equals(perfilAvancado.getApelido())){
                perfilAvancado.setContadorId(perfilAvancado.getContadorId() - 1);
                throw new PerfilJaExisteError("Já existe um perfil com apelido!");
            }else if(perfil.getEmail().equals(perfilAvancado.getEmail())){
                perfilAvancado.setContadorId(perfilAvancado.getContadorId() - 1);
                throw new PerfilJaExisteError("Já existe um perfil com esse e-mail!");
            }
        }
        return perfil;
    }

    public void pecorrerListaDePerfis(RedeSocial redeSocial) {
        for (PerfilAvancado perfil : redeSocial.getPerfil()) {
            if (perfil.getStatus()) {
                mostrarMensagem(String.format("\t\r| ID: %d, Nome: %s, Email: %s, Emoji: %s |",
                        perfil.getId(), perfil.getApelido(), perfil.getEmail(), perfil.getEmoji()));
            }
        }
    }

    public void pecorrerListaDePublicacoes(ArrayList<PublicacaoAvancada> publicacoes) {
        SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        for (PublicacaoAvancada publicacao : publicacoes) {
            String data = dataFormatada.format(publicacao.getDataPublicacao());
            mostrarMensagem(String.format("\t\r| ID: %d, Conteúdo: %s, Data de Publição: %s , Interações: %s |",
                    publicacao.getId(), publicacao.getConteudo(), data, publicacao.listarInteracoes()));
        }
    }

    public void pecorrerListaDeEmoji(String[] arrayDeEmoji) {
        for (int i = 0; i < arrayDeEmoji.length; i++) {
            if (i > 0 && i % 10 == 0) {
                System.out.println();
            }

            System.out.printf("%2d - %s ", i + 1, arrayDeEmoji[i]);
        }
        System.out.println();
    }
}
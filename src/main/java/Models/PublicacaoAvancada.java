package Models;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PublicacaoAvancada extends Publicacao {
    // Atributos
    @JsonProperty("Interacoes") //Para a serialização funcionar
    private ArrayList<Interacao> _interacoes;

    // Construtor padrão
    public PublicacaoAvancada() {
        super();
        this._interacoes = new ArrayList<>();
    }

    // Construtor
    public PublicacaoAvancada(String conteudo, PerfilAvancado perfilAssociado, ArrayList<Interacao> interacoes) {
        super(conteudo, perfilAssociado);
        this._interacoes = interacoes;
    }
    
    // Metodos
    public void adicionarInteracao(Interacao interacao) {
        this._interacoes.add(interacao);
    }

    public ArrayList<Interacao> listarInteracoes() {
        return new ArrayList<>(this._interacoes);
    }

    public boolean jaInteragiu(PerfilAvancado perfil) {
        for (Interacao interacao : this._interacoes) {
            if (interacao.getPerfilAutor().equals(perfil)) {
                return true;
            }
        }
        return false;
    }
}
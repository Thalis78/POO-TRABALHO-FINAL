package Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "apelido")
public class Perfil {
    // Atributos
    private static int contadorId = 1;
    private int _id;
    private String _apelido;
    private EmojiConverter _emoji;
    private String _email;
    private boolean _status;
    private ArrayList<PublicacaoAvancada> _postagens;

    @JsonIgnore
    private ArrayList<PerfilAvancado> _amigos;

    // Construtor padrão
    public Perfil() {
        this._id = ++contadorId;
        this._amigos = new ArrayList<>();
        this._postagens = new ArrayList<>();
        this._status = true;
    }

    public Perfil(String apelido, EmojiConverter emoji, String email) {
        this._id = contadorId;
        this._apelido = apelido;
        this._emoji = emoji;
        this._email = email;
        this._status = true;
        this._amigos = new ArrayList<>();
        this._postagens = new ArrayList<>();
    }

    // Getters e Setters
    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public String getApelido() {
        return this._apelido;
    }

    public void setApelido(String apelido) {
        this._apelido = apelido;
    }

    public EmojiConverter getEmoji() {
        return this._emoji;
    }

    @JsonIgnore
    public int getContadorId() {
        return contadorId;
    }

    public void setContadorId(int contadorId) {
        Perfil.contadorId = contadorId;
    }

    public void setEmoji(EmojiConverter emoji) {
        this._emoji = emoji;
    }

    public Boolean getStatus() {
        return this._status;
    }

    public void setStatus(Boolean status) {
        this._status = status;
    }

    public String getEmail() {
        return this._email;
    }

    public void setEmail(String email) {
        this._email = email;
    }

    public ArrayList<PerfilAvancado> getAmigos() {
        return this._amigos;
    }

    // Metodo especifico para serializacao de amigos
    @JsonProperty("amigos")
    public List<String> getAmigosApelidosParaJson() {
        return this._amigos.stream()
                .map(PerfilAvancado::getApelido)
                .collect(Collectors.toList());
    }


    public void setAmigos(ArrayList<PerfilAvancado> amigos) {
        this._amigos = amigos;
    }

    // Métodos
    public void adicionarAmigo(PerfilAvancado amigo) {
        if (!this._amigos.contains(amigo)) {
            this._amigos.add(amigo);
            if (!amigo.getAmigos().contains(this)) { // Evita chamada recursiva infinita para adicionar amigos
                amigo.getAmigos().add((PerfilAvancado) this);
            }
        }
    }

    public void removerAmigo(PerfilAvancado amigo) {
        if (this._amigos.contains(amigo)) {
            this._amigos.remove(amigo);
            if (amigo.getAmigos().contains(this)) {
                amigo.getAmigos().remove(this); // Evita chamada recursiva infinita para remover amigos
            }
        }
    }

    public void removerPostagem(PublicacaoAvancada postagem) {
        this._postagens.remove(postagem);
    }

    public void adicionarPostagem(PublicacaoAvancada postagem) {
        this._postagens.add(postagem);
    }

    public ArrayList<PerfilAvancado> listarAmigos() {
        return new ArrayList<>(this._amigos);
    }

    public ArrayList<PublicacaoAvancada> listarPostagens() {
        return new ArrayList<>(this._postagens);
    }

    public void ativarPerfil() {
        this._status = true;
    }

    public void desativarPerfil() {
        this._status = false;
    }
}
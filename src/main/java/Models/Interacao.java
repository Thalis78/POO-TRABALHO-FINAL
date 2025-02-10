package Models;

import Enum.TipoInteracao;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Interacao {
    // Atributos
    private static int contadorId = 0;
    private int _id;
    private TipoInteracao _tipo;
    private PerfilAvancado _perfilAutor;

    // Construtor
    public Interacao(TipoInteracao tipo, PerfilAvancado perfilAutor) {
        this._id = ++contadorId;
        this._tipo = tipo;
        this._perfilAutor = perfilAutor;
    }

    // Construtor padrão
    public Interacao() {
        this._id = ++contadorId;
    }

    // Getters e Setters
    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public TipoInteracao getTipo() {
        return _tipo;
    }

    public void setTipo(TipoInteracao tipo) {
        this._tipo = tipo;
    }

    public PerfilAvancado getPerfilAutor() {
        return _perfilAutor;
    }

    @JsonProperty("perfilAutor")
    public String getPerfilAutorApelido() {
        return _perfilAutor != null ? _perfilAutor.getApelido() : null;
    }

    public void setPerfilAutor(PerfilAvancado perfilAutor) {
        this._perfilAutor = perfilAutor;
    }

    @Override
    public String toString() {
        return _tipo.toString();
    }
}
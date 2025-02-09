package Models;

import java.util.Date;

public class Publicacao {
    // Atributos
    private static int contadorId = 0;
    private int _id;
    private String _conteudo;
    private Date _dataPublicacao;
    private PerfilAvancado _perfilAssociado;

    // Construtor padrão
    public Publicacao() {
        this._id = ++contadorId;
        this._conteudo = "";
        this._dataPublicacao = new Date();
    }

    // Construtor
    public Publicacao(String conteudo, PerfilAvancado perfilAssociado) {
        this._id = ++contadorId;
        this._conteudo = conteudo;
        this._dataPublicacao = new Date();
        this._perfilAssociado = perfilAssociado;
    }

    // Getters e Setters
    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public String getConteudo() {
        return _conteudo;
    }

    public void setConteudo(String conteudo) {
        this._conteudo = conteudo;
    }

    public Date getDataPublicacao() {
        return _dataPublicacao;
    }

    public void setDataPublicacao(Date dataPublicacao) {
        this._dataPublicacao = dataPublicacao;
    }

    public PerfilAvancado getPerfilAssociado() {
        return _perfilAssociado;
    }

    public void setPerfilAssociado(PerfilAvancado _perfilAssociado) {
        this._perfilAssociado = _perfilAssociado;
    }
}
package Models;

public class PerfilAtualLogado {
    private PerfilAvancado _perfilAtualLogado;

    // Construtor padrao
    public PerfilAtualLogado() {
    }

    // Getters e Setters
    public PerfilAvancado getPerfilAtual() {
        return _perfilAtualLogado;
    }

    public void setPerfilAtual(PerfilAvancado perfil) {
        this._perfilAtualLogado = perfil;
    }
}
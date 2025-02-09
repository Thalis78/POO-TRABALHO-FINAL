package Models;

public class PedidoDeAmizade {
    private PerfilAvancado _perfilSolicitante;
    private PerfilAvancado _perfilSolicitado;

    // Construtor padrao
    public PedidoDeAmizade() {
    }

    public PedidoDeAmizade(PerfilAvancado perfilSolicitante, PerfilAvancado perfilSolicitado) {
        this._perfilSolicitante = perfilSolicitante;
        this._perfilSolicitado = perfilSolicitado;
    }

    public PerfilAvancado getPerfilSolicitante() {
        return _perfilSolicitante;
    }

    public void setPerfilSolicitante(PerfilAvancado perfilSolicitante) {
        this._perfilSolicitante = perfilSolicitante;
    }

    public PerfilAvancado getPerfilSolicitado() {
        return _perfilSolicitado;
    }

    public void setPerfilSolicitado(PerfilAvancado perfilSolicitado) {
        this._perfilSolicitado = perfilSolicitado;
    }
}

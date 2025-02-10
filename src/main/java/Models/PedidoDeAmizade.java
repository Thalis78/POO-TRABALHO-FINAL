package Models;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    @JsonProperty("perfilSolicitante")
    public String getPerfilSolicitanteApelido() {
        return _perfilSolicitante != null ? _perfilSolicitante.getApelido() : null;
    }

    public void setPerfilSolicitante(PerfilAvancado perfilSolicitante) {
        this._perfilSolicitante = perfilSolicitante;
    }

    @JsonProperty("perfilSolicitado")
    public String getPerfilSolicitadoApelido() {
        return _perfilSolicitado != null ? _perfilSolicitado.getApelido() : null;
    }

    public PerfilAvancado getPerfilSolicitado() {
        return _perfilSolicitado;
    }

    public void setPerfilSolicitado(PerfilAvancado perfilSolicitado) {
        this._perfilSolicitado = perfilSolicitado;
    }
}

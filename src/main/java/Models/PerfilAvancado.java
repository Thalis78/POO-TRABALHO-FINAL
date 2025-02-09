package Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "apelido")
public class PerfilAvancado extends Perfil {

    // Construtor padrão
    public PerfilAvancado() {
        super();
    }
    
    // Construtor
	public PerfilAvancado(String apelido, EmojiConverter emoji, String email) {
		super(apelido, emoji, email);
	}

    // Métodos
    public void habilitarPerfil(PerfilAvancado perfil) {
        perfil.setStatus(true);
    }

    public void desabilitarPerfil(PerfilAvancado perfil) {
        perfil.setStatus(false);
    }
    
}
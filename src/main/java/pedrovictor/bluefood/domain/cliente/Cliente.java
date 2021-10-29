package pedrovictor.bluefood.domain.cliente;

import javax.persistence.Entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pedrovictor.bluefood.domain.usuario.Usuario;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
@SuppressWarnings("serial")
public class Cliente extends Usuario {
	

	private String cpf;
	
	private String cep;
	
	

}

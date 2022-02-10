package pedrovictor.bluefood.domain.usuario;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuppressWarnings("serial")
@MappedSuperclass
public class Usuario implements Serializable {


	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "O Nome n�o pode ser vazio")
	@Size(max = 90, message = "O nome � muito grande")
	private String nome;
	
	@NotBlank(message = "O Email n�o pode ser vazio")
	@Size(message= "O e-mail � muito grande", max = 60)
	@Email(message = "O e-mail n�o � v�lido")
	private String email;
	
	@NotBlank(message = "A senha n�o pode ser vazia")
	@Size(max = 80, message = "A senha � muito grande")
	private String senha;
	
	@NotBlank(message = "O telefone n�o pode ser vazio")
	@Pattern(regexp="[0-9], {11}", message = "O formato do telefone � inv�lido")
	@Column(length = 11, nullable = false)
	private String telefone;
	
	
}

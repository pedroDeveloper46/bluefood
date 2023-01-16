package pedrovictor.bluefood.domain.restaurante;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pedrovictor.bluefood.domain.usuario.Usuario;

@SuppressWarnings("serial")
@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Table(name = "restaurante")
public class Restaurante extends Usuario {

   @NotBlank(message = "O CNPJ n�o pode ser nulo")
   @Pattern(regexp="[0-9]{14}", message="O CNPJ possui formato inv�lido")
   @Column(length=14, nullable = false)
   private String cnpj;
   
   @Size(max = 80)
   private String logotipo;
   
   @NotNull(message = "A taxa n�o pode ser vazia")
   @Max(99)
   @Min(0)
   private BigDecimal taxaEntrega;
   
   @NotNull(message = "O tempo de entrega n�o pode ser vazio")
   @Max(120)
   @Min(0)
   private Integer tempoEntregaBase;
   
   @ManyToMany
   @JoinTable(
	  name = "restaurante_has_categoria",
	  joinColumns = @JoinColumn(name = "restaurante_id"),
	  inverseJoinColumns = @JoinColumn(name = "categoria_id")
   )
   private Set<CategoriaRestaurante> categorias = new HashSet<>(0);
   
   
   
}

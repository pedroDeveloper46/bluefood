package pedrovictor.bluefood.domain.restaurante;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pedrovictor.bluefood.domain.usuario.Usuario;
import pedrovictor.bluefood.infrastructure.web.validator.UploadConstraint;
import pedrovictor.bluefood.util.FileType;
import pedrovictor.bluefood.util.StringUtils;

@SuppressWarnings("serial")
@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Table(name = "restaurante")
public class Restaurante extends Usuario {

	@NotBlank(message = "O CNPJ não pode ser nulo")
	@Pattern(regexp = "[0-9]{14}", message = "O CNPJ possui formato inválido")
	@Column(length = 14, nullable = false)
	private String cnpj;

	@Size(max = 80)
	private String logotipo;

	@UploadConstraint(acceptedTypes = { FileType.JPG, FileType.PNG }, message = "o Arquivo de imagem não é válido")
	private transient MultipartFile logotipoFile;

	@NotNull(message = "A taxa não pode ser vazia")
	@Max(99)
	@Min(0)
	private BigDecimal taxaEntrega;

	@NotNull(message = "O tempo de entrega não pode ser vazio")
	@Max(120)
	@Min(0)
	private Integer tempoEntregaBase;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "restaurante_has_categoria", joinColumns = @JoinColumn(name = "restaurante_id"), 
	inverseJoinColumns = @JoinColumn(name = "categoria_id"))
	@Size(min = 1, message = "O restaurante precisa de pelo menos uma categoria")
	@ToString.Exclude
	private Set<CategoriaRestaurante> categorias = new HashSet<>(0);

	@OneToMany(mappedBy = "restaurante")
	private Set<ItemCardapio> itensCardapio = new HashSet<>(0);

	public void setLogotipoFileName() {
		if (getId() == null) {
			throw new IllegalStateException("É preciso primeiro gravar o Restaurante");
		}

		this.logotipo = String.format("%04d-logo.%s", getId(), FileType.of(logotipoFile.getContentType()));
	}

	public String getCategoriasAsText() {
		Set<String> categoriasNomes = new LinkedHashSet<>();

		for (CategoriaRestaurante categoria : categorias) {
           categoriasNomes.add(categoria.getNome());
		}
		
		return StringUtils.concatenate(categoriasNomes);

	}
	
	public Integer calcularTempoEntrega(String cep) {
		
		int soma = 0;
		
		for (char c : cep.toCharArray()) {
			
			int n = Character.getNumericValue(c);
			
			if (n > 0) {
				soma += n;
			}
			
		}
		
		soma /= 2;
		
		
		return soma + this.getTempoEntregaBase();
	}

}

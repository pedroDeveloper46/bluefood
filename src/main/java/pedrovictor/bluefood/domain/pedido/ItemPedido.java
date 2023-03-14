package pedrovictor.bluefood.domain.pedido;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pedrovictor.bluefood.domain.restaurante.ItemCardapio;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode( onlyExplicitlyIncluded = true)
@Entity
@Table(name = "item_pedido")
public class ItemPedido implements Serializable {
	
	@EmbeddedId
	@EqualsAndHashCode.Include
	private ItemPedidoPK id;
	
	@ManyToOne
	@NotNull
	private ItemCardapio itemCardapio;
	
	@NotNull
	private Integer quantidade;
	
	@Size(max = 50)
	private String observacoes;
	
	@NotNull
	private BigDecimal preco;
	
	public boolean observacoesIsEmpty() {
		return getObservacoes().isEmpty();
	}
	
	
	public BigDecimal getPrecoCalculado() {
		return preco.multiply(BigDecimal.valueOf(quantidade));		
	}
	

}

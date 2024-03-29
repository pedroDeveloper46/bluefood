package pedrovictor.bluefood.domain.pagamento;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pedrovictor.bluefood.domain.pedido.Pedido;

@SuppressWarnings("serial")
@Entity
@Table(name = "pagamento")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pagamento implements Serializable {
	
	@Id
	private Integer id;
     
	@OneToOne
	@NotNull
	@MapsId
	private Pedido pedido;
	
	@NotNull
	private LocalDateTime data;
	
	@Column(name = "num_cartao_final")
	@NotNull
	@Size(min = 4, max = 4)
	private String numCartaoFinal;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private BandeiraCartao bandeiraCartao;
	
	
	public void definirNumeroEBandeiro(String numCartao) {
		
		numCartaoFinal = numCartao.substring(12);
		
		this.bandeiraCartao = getBandeira(numCartao);
		  
	}
	
	
	private BandeiraCartao getBandeira(String numCartao) {
		
		if (numCartao.startsWith("0000")) {
			return BandeiraCartao.AMEX;
		}
		
		if (numCartao.startsWith("1111")) {
			return BandeiraCartao.ELO;
		}
		
		if (numCartao.startsWith("2222")) {
			return BandeiraCartao.MASTER;
		}
		
		
		return BandeiraCartao.VISA;
	}
	
	
}

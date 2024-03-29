package pedrovictor.bluefood.domain.pedido;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RelatorioItemFaturamento {
	
	private String nome;
	
	private Long quantidade;
	
	private BigDecimal valor;

}

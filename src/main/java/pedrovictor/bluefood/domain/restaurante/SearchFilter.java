package pedrovictor.bluefood.domain.restaurante;



import lombok.Data;
import pedrovictor.bluefood.util.StringUtils;

@Data
public class SearchFilter {
	
	public enum SearchType{
		Texto,
		Categoria
	}
	
	public enum Order{
		Taxa,
		Tempo,	
	}
	
	public enum Command{
		EntregaGratis, MaiorTaxa, MenorTaxa, MaiorTempo, MenorTempo;
	}
	
	private String texto;
	
	private SearchType searchType;
	
	private Integer categoriaId;
	
	private Order order = Order.Taxa;
	
	private boolean asc;
	
	private boolean entregaGratis;
	
	
	
	public void processFilter(String cmd) {
		
		
		if (!StringUtils.isEmpty(cmd)) {
			
			Command command = Command.valueOf(cmd);
				
			if (command == Command.EntregaGratis) {
				entregaGratis = !entregaGratis;
			}else if (command == Command.MaiorTaxa) {
				order = Order.Taxa;
				asc = false;
			}else if (command == Command.MenorTaxa) {
				order = Order.Taxa;
				asc = true;
			}else if (command == Command.MaiorTempo) {
				order = Order.Tempo;
				asc = false;
			}else if (command == Command.MenorTempo) {
				order = Order.Tempo;
				asc = true;
			}
		}
		
		
		if (searchType == SearchType.Texto) {
			categoriaId = 0;
		}else if (searchType == SearchType.Categoria) {
			texto = null;
		}
		
		
	}

}

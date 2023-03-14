package pedrovictor.bluefood.domain.restaurante;

import java.util.Comparator;

import pedrovictor.bluefood.domain.restaurante.SearchFilter.Order;

public class RestauranteComparator implements Comparator<Restaurante> {

	private SearchFilter filter;
	
	private String cep;
	
	public RestauranteComparator(SearchFilter filter, String cep) {
		super();
		this.filter = filter;
		this.cep = cep;
	}

	@Override
	public int compare(Restaurante r1, Restaurante r2) {
		// TODO Auto-generated method stub
		
		int result;
		
		if (filter.getOrder() == Order.Taxa) {
			result = r1.getTaxaEntrega().compareTo(r2.getTaxaEntrega());
		}else if (filter.getOrder() == Order.Tempo) {
			result = r1.calcularTempoEntrega(cep).compareTo(r2.calcularTempoEntrega(cep));
		}else {
			throw new IllegalStateException(filter.getSearchType()+ " não é um tipo de filtro válido");
		}
		
	    return filter.isAsc() ? result : -result;
	}

}

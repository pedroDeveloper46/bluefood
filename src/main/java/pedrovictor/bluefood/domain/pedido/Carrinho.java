package pedrovictor.bluefood.domain.pedido;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.Data;
import pedrovictor.bluefood.domain.restaurante.ItemCardapio;
import pedrovictor.bluefood.domain.restaurante.Restaurante;
@SuppressWarnings("serial")
@Data
public class Carrinho implements Serializable {

	private List<ItemPedido> itens = new ArrayList<>();

	private Restaurante restaurante;

	public void adicionarItem(ItemCardapio item, Integer quantidade, String observacoes)
			throws RestauranteDiferenteException {

		if (itens.size() == 0) {
			restaurante = item.getRestaurante();
		} else if (!item.getRestaurante().getId().equals(restaurante.getId())) {
			throw new RestauranteDiferenteException("Não é possível em comprar em mais de um restaurante");
		}

		if (!exists(item)) {
			ItemPedido itemPedido = new ItemPedido();
			itemPedido.setItemCardapio(item);
			itemPedido.setPreco(item.getPreco());
			itemPedido.setObservacoes(observacoes);
			itemPedido.setQuantidade(quantidade);

			itens.add(itemPedido);

		}

	}
	
	public void removerItem(ItemCardapio itemCardapio) {
		
		for (Iterator<ItemPedido> iterator = itens.iterator(); iterator.hasNext();) {
			ItemPedido pedido = iterator.next();
			
			if (pedido.getItemCardapio().getId().equals(itemCardapio.getId())) {
				iterator.remove();
				break;
			}
			
		}
		
		if (itens.size() == 0) {
			restaurante = null;
		}
		
	}
	
	public void adicionarItem(ItemPedido itemPedido) {
		try {
			adicionarItem(itemPedido.getItemCardapio(), itemPedido.getQuantidade(), itemPedido.getObservacoes());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	


	private boolean exists(ItemCardapio itemCardapio) {

		for (ItemPedido itemPedido : itens) {
			if (itemPedido.getItemCardapio().getId().equals(itemCardapio.getId())) {
				return true;
			}
		}

		return false;

	}
	
	public BigDecimal getPrecoTotal(boolean adicionarTaxaEntrega) {
		
		BigDecimal soma = BigDecimal.ZERO;
		
		for (ItemPedido itemPedido : itens) {
			soma = soma.add(itemPedido.getPrecoCalculado());
		}
		
		if (adicionarTaxaEntrega) {
			soma = soma.add(restaurante.getTaxaEntrega());
		}
		
		return soma;
		
		
		
	}
	
	public void limpar() {
		itens.clear();
		restaurante = null;
	}
	
	public boolean vazio() {
		return itens.size() == 0;
	}
	
	

}


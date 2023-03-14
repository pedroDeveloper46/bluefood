package pedrovictor.bluefood.infrastructure.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import pedrovictor.bluefood.domain.pedido.Carrinho;
import pedrovictor.bluefood.domain.pedido.ItemPedido;
import pedrovictor.bluefood.domain.pedido.Pedido;
import pedrovictor.bluefood.domain.pedido.PedidoRepository;
import pedrovictor.bluefood.domain.pedido.RestauranteDiferenteException;
import pedrovictor.bluefood.domain.restaurante.ItemCardapio;
import pedrovictor.bluefood.domain.restaurante.ItemCardapioRepository;

@Controller
@RequestMapping(path = "/cliente/carrinho")
@SessionAttributes("carrinho")
public class CarrinhoController {

	@Autowired
	private ItemCardapioRepository cardapioRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@ModelAttribute("carrinho")
	public Carrinho carrinho() {
		return new Carrinho();
	}
	
	@GetMapping(path = "/adicionar")
	public String adiconarItem(
			@RequestParam("itemId") Integer id, 
			@RequestParam("quantidade") Integer quantidade, 
			@RequestParam("observacoes") String observacoes,
			@ModelAttribute("carrinho") Carrinho carrinho,
			Model model) {
				
		ItemCardapio cardapio = cardapioRepository.findById(id).orElseThrow();
		
		try {
			carrinho.adicionarItem(cardapio, quantidade, observacoes);
		} catch (RestauranteDiferenteException e) {
			// TODO Auto-generated catch block
			model.addAttribute("msg", e.getMessage());
		}
		
		
		
		return "cliente-carrinho";
	}
	
	@GetMapping(path = "/remover")
	public String removerItem(
			@RequestParam("itemId") Integer itemId,
			@ModelAttribute("carrinho") Carrinho carrinho,
			SessionStatus sessionStatus,
			Model model) {
		ItemCardapio cardapio = cardapioRepository.findById(itemId).orElseThrow();
		
		carrinho.removerItem(cardapio);
		
		if (carrinho.vazio()) {
			sessionStatus.setComplete();
		}
		
		return "cliente-carrinho";
	}
	
	
	
	@GetMapping(path = "/visualizar")
	public String viewCarrinho() {
		
		return "cliente-carrinho";
		
		
	}
	
	@GetMapping(path = "/refazerCarrinho")
	public String refazerCarrinho(
			@RequestParam("pedidoId") Integer pedidoId,
			@ModelAttribute("carrinho") Carrinho carrinho,
			Model model) {
		
		Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow();
		
		carrinho.limpar();
		
		for (ItemPedido itemPedido: pedido.getItens()) {
			carrinho.adicionarItem(itemPedido);
		}
		
		return "cliente-carrinho";
		
	}
}

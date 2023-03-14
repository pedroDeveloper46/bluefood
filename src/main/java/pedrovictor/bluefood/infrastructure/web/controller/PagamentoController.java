package pedrovictor.bluefood.infrastructure.web.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import pedrovictor.bluefood.application.service.ImageService;
import pedrovictor.bluefood.application.service.PagamentoException;
import pedrovictor.bluefood.application.service.PedidoService;
import pedrovictor.bluefood.domain.pedido.Carrinho;
import pedrovictor.bluefood.domain.pedido.Pedido;

@Controller
@RequestMapping("/cliente/pagamento")
@SessionAttributes("carrinho")
public class PagamentoController {
	
	@Autowired
	private PedidoService pedidoService;
	
	@PostMapping(path="/pagar")
	public String pagar(@RequestParam("numCartao") String numCartao,
			@ModelAttribute("carrinho") Carrinho carrinho,
			SessionStatus sessionStatus,
			Model model) {
		
		
		try {
			Pedido pedido = pedidoService.criaEPagar(carrinho, numCartao);
			
			sessionStatus.setComplete();
			
			return "redirect:/cliente/pedido/view?pedidoId="+ pedido.getId();
			
		} catch (PagamentoException e) {
			model.addAttribute("msg", e.getMessage());
			return "cliente-carrinho";
		}
		
		
	}

}

package pedrovictor.bluefood.infrastructure.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pedrovictor.bluefood.application.service.RelatorioService;
import pedrovictor.bluefood.application.service.RestauranteService;
import pedrovictor.bluefood.application.service.ValidationException;
import pedrovictor.bluefood.domain.pedido.Pedido;
import pedrovictor.bluefood.domain.pedido.PedidoRepository;
import pedrovictor.bluefood.domain.pedido.RelatorioItemFaturamento;
import pedrovictor.bluefood.domain.pedido.RelatorioItemFilter;
import pedrovictor.bluefood.domain.pedido.RelatorioPedidoFilter;
//import pedrovictor.bluefood.domain.cliente.Cliente;
import pedrovictor.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import pedrovictor.bluefood.domain.restaurante.ItemCardapio;
import pedrovictor.bluefood.domain.restaurante.ItemCardapioRepository;
import pedrovictor.bluefood.domain.restaurante.Restaurante;
import pedrovictor.bluefood.domain.restaurante.RestauranteRepository;
import pedrovictor.bluefood.util.SecurityUtils;

@Controller
@RequestMapping(path = "/restaurante")
public class RestauranteController {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private CategoriaRestauranteRepository categoriaRestauranteRepository;
	
	@Autowired
	private ItemCardapioRepository cardapioRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private RelatorioService relatorioService;
	
	@GetMapping(path = "/home")
	public String home(Model model) {
		
		Integer id = SecurityUtils.loggedCRestaurante().getId();
		List<Pedido> pedidos = pedidoRepository.findByRestaurante_idOrderByDataDesc(id);
		
		model.addAttribute("pedidos", pedidos);
		
		return "restaurante-home";
	}
	
	@GetMapping(path= "/edit")
	public String edit(Model model) {
		
		Integer id = SecurityUtils.loggedCRestaurante().getId();
		Restaurante restaurante = restauranteRepository.findById(id).orElseThrow();
		model.addAttribute("restaurante", restaurante);
		ControllerHelper.setEditMode(model, true);
		ControllerHelper.addCategoriasToRequest(categoriaRestauranteRepository, model);
		return "restaurante-cadastro";
	}
	
	@PostMapping(path = "/save")
	public String saveRestaurante(
			@ModelAttribute("restaurante") @Valid Restaurante restaurante,
			Model model,
			Errors erros
			) {
		
		if (!erros.hasErrors()) {
			try {
				restauranteService.saveRestaurante(restaurante);
				model.addAttribute("msg", "Restaurante alterado com sucesso");
			} catch (ValidationException e) {
				erros.rejectValue("email",null, e.getMessage());
			}
		}
		
		ControllerHelper.setEditMode(model, true);
		ControllerHelper.addCategoriasToRequest(categoriaRestauranteRepository, model);
		return "restaurante-cadastro";
	}
	
	@GetMapping(path ="/comidas")
	public String viewComidas(Model model) {
	    
		Integer restauranteId = SecurityUtils.loggedCRestaurante().getId();
		
		Restaurante restaurante = restauranteRepository.findById(restauranteId).orElseThrow();
		
		model.addAttribute("restaurante", restaurante);
		
		model.addAttribute("itemCardapio", new ItemCardapio());
		
		List<ItemCardapio> itens = cardapioRepository.findByRestaurante_IdOrderByNome(restauranteId);
		
		model.addAttribute("itensCardapio", itens);
		
		return "restaurante-comidas";
		
	}
	
	@GetMapping(path = "/comidas/remover")
	public String remover(@RequestParam("itemId") Integer id,
			Model model) {
		
	    ItemCardapio item = cardapioRepository.findById(id).orElseThrow();
	    
	    cardapioRepository.deleteById(id);
	    
		return "redirect:/restaurante/comidas";
	}
	
	@PostMapping(path = "/comidas/cadastrar")
	public String cadastrar(@Valid @ModelAttribute("itemCardapio") ItemCardapio cardapio,
			Errors errors,
			Model model) {
		
		
		
		if (errors.hasErrors()) {
			
			Integer restauranteId = SecurityUtils.loggedCRestaurante().getId();
			
			Restaurante restaurante = restauranteRepository.findById(restauranteId).orElseThrow();
			
			model.addAttribute("restaurante", restaurante);
			
			List<ItemCardapio> itens = cardapioRepository.findByRestaurante_IdOrderByNome(restauranteId);
			
			model.addAttribute("itensCardapio", itens);
			
			return "restaurante-comidas";
			
		}
		
		restauranteService.saveItemCardapio(cardapio);
		
		return "redirect:/restaurante/comidas";
	}
	
	@GetMapping(path = "/pedido")
	public String viewPedido(@RequestParam("pedidoId") Integer pedidoId,
			Model model) {
		
		Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow();
		
		model.addAttribute("pedido", pedido);
		
		return "restaurante-pedido";
		
		
	}
	
	@PostMapping(path="/pedido/proximoStatus")
	public String proximo(@RequestParam("pedidoId") Integer id, Model model) {
		
		Pedido pedido = pedidoRepository.findById(id).orElseThrow();
		pedido.definirProximoStatus();
		
		pedidoRepository.save(pedido);
		
		model.addAttribute("pedido", pedido);
		
		model.addAttribute("msg", "Status alterado com sucesso");
		
		
		
		return "restaurante-pedido";
		
	}
	
	@GetMapping(path = "/relatorio/pedidos")
	public String relatorioPedidos(@ModelAttribute("filter") RelatorioPedidoFilter filter,
			Model model) {
		
		Integer restauranteId = SecurityUtils.loggedCRestaurante().getId();
		
		List<Pedido> pedidos = relatorioService.listPedidos(restauranteId, filter);
		
		model.addAttribute("pedidos", pedidos);
		
		model.addAttribute("filter", filter);
		
		return "restaurante-relatorio-pedidos";
		
		
	}
	
	@GetMapping(path = "/relatorio/itens")
	public String relatorioPedidos(@ModelAttribute("filter") RelatorioItemFilter filter,
			Model model) {
		
		Integer restauranteId = SecurityUtils.loggedCRestaurante().getId();
		
		List<ItemCardapio> itens = cardapioRepository.findByRestaurante_IdOrderByNome(restauranteId);
		
		model.addAttribute("itens", itens);
		
		List<RelatorioItemFaturamento> itensCalculados = relatorioService.calcularFaturamentoItens(restauranteId, filter);
		model.addAttribute("itensCalculados", itensCalculados);
		
        model.addAttribute("filter", filter);
		
		return "restaurante-relatorio-itens";
		
		
	}
	


}

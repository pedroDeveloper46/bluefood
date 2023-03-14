package pedrovictor.bluefood.infrastructure.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pedrovictor.bluefood.application.service.ClienteService;
import pedrovictor.bluefood.application.service.RestauranteService;
import pedrovictor.bluefood.application.service.ValidationException;
import pedrovictor.bluefood.domain.cliente.Cliente;
import pedrovictor.bluefood.domain.cliente.ClienteRepository;
import pedrovictor.bluefood.domain.pedido.Pedido;
import pedrovictor.bluefood.domain.pedido.PedidoRepository;
import pedrovictor.bluefood.domain.restaurante.CategoriaRestaurante;
import pedrovictor.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import pedrovictor.bluefood.domain.restaurante.ItemCardapio;
import pedrovictor.bluefood.domain.restaurante.ItemCardapioRepository;
import pedrovictor.bluefood.domain.restaurante.Restaurante;
import pedrovictor.bluefood.domain.restaurante.RestauranteRepository;
import pedrovictor.bluefood.domain.restaurante.SearchFilter;
import pedrovictor.bluefood.infrastructure.web.security.LoggedUser;
import pedrovictor.bluefood.util.SecurityUtils;
import pedrovictor.bluefood.util.StringUtils;


@Controller
@RequestMapping(path = "/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private CategoriaRestauranteRepository categoriaRestauranteRepository;
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private ItemCardapioRepository cardapioRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	
	@GetMapping(path = "/home")
	public String home(Model model) {
		
		List<CategoriaRestaurante> categorias = categoriaRestauranteRepository.findAll(Sort.by("nome"));
		
		model.addAttribute("categorias", categorias);
		model.addAttribute("searchFilter", new SearchFilter());
		
		List<Pedido> pedidos = pedidoRepository.findByCliente_id(SecurityUtils.loggedCliente().getId());
		
		model.addAttribute("pedidos", pedidos);
		
		return "cliente-home";
	}
	
	@GetMapping(path= "/edit")
	public String edit(Model model) {
		
		Integer id = SecurityUtils.loggedCliente().getId();
		Cliente cliente = clienteRepository.findById(id).orElseThrow();
		model.addAttribute("cliente", cliente);
		ControllerHelper.setEditMode(model, true);
		return "cliente-cadastro";
	}
	
	@PostMapping(path = "/save")
	public String saveCliente(@ModelAttribute("cliente") @Valid Cliente cliente, 
			Errors erros, Model model) {
			
		if(!erros.hasErrors()) {
			try {
				clienteService.saveCliente(cliente);
				model.addAttribute("msg", "Cliente atualizado com sucesso!");
			} catch (ValidationException e) {
				// TODO Auto-generated catch block
				erros.rejectValue("email",null, e.getMessage());
			}
			
		}
		
		
		ControllerHelper.setEditMode(model, true);
		
		return "cliente-cadastro";
		
	}
	
	
	@GetMapping(path = "/search")
	public String search(@ModelAttribute("searchFilter") 
	SearchFilter searchFilter,
	@RequestParam(value = "cmd", required = false) String cmd,
    Model model) {
		
		searchFilter.processFilter(cmd);
		
	    ControllerHelper.addCategoriasToRequest(categoriaRestauranteRepository, model);
	    List<Restaurante> restaurantes = restauranteService.search(searchFilter);
	    
		model.addAttribute("restaurantes", restaurantes);
		model.addAttribute("searchFilter", searchFilter);
		
		return "cliente-busca";
	}
	
	@GetMapping(path = "/restaurante")
	public String viewRestaurante(@RequestParam("restauranteId") Integer restauranteId, 
			Model model,
			@RequestParam(required = false, value = "categoria") String categoria) {
		
		Restaurante restaurante = restauranteRepository.findById(restauranteId).orElseThrow();
		
		model.addAttribute("restaurante", restaurante);
		
		model.addAttribute("cep", SecurityUtils.loggedCliente().getCep());
		
		List<String> categorias = cardapioRepository.findCategorias(restauranteId);
		
		model.addAttribute("categorias", categorias);
		
		List<ItemCardapio> itensCardapioDestaque;
		List<ItemCardapio> itensCardapioNaoDestaque;
		
		if (categoria == null) {
			itensCardapioDestaque = cardapioRepository.findByRestaurante_IdAndDestaqueOrderByNome(restauranteId, true);
			itensCardapioNaoDestaque = cardapioRepository.findByRestaurante_IdAndDestaqueOrderByNome(restauranteId, false);
		}else {
			itensCardapioDestaque = cardapioRepository.findByRestaurante_IdAndDestaqueAndCategoriaOrderByNome(restauranteId, true, categoria);
			itensCardapioNaoDestaque = cardapioRepository.findByRestaurante_IdAndDestaqueAndCategoriaOrderByNome(restauranteId, false, categoria);
		}
		
		model.addAttribute("itensCardapioDestaque", itensCardapioDestaque);
		model.addAttribute("itensCardapioNaoDestaque", itensCardapioNaoDestaque);
		model.addAttribute("categoriaSelecionada", categoria);
		
		return "cliente-restaurante";
		
		
	}
	
	
	
	
	
	

}

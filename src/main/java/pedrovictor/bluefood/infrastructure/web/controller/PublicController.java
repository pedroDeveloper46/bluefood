package pedrovictor.bluefood.infrastructure.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pedrovictor.bluefood.application.service.ClienteService;
import pedrovictor.bluefood.application.service.RestauranteService;
import pedrovictor.bluefood.application.service.ValidationException;
import pedrovictor.bluefood.domain.cliente.Cliente;
import pedrovictor.bluefood.domain.restaurante.Restaurante;
import pedrovictor.bluefood.domain.restaurante.CategoriaRestauranteRepository;

@Controller
@RequestMapping(path="/public")
public class PublicController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired	
	private RestauranteService restauranteService;
	
	@Autowired
	private CategoriaRestauranteRepository categoriaRestauranteRepository;
	
	
	@GetMapping("/cliente/new")
	public String newCliente(Model model) {
		model.addAttribute("cliente", new Cliente());
		ControllerHelper.setEditMode(model, false);
		return "cliente-cadastro";
		
	}
	
	@PostMapping(path = "/cliente/save")
	public String saveCliente(@ModelAttribute("cliente") @Valid Cliente cliente, 
			Errors erros, Model model) {
			
		if(!erros.hasErrors()) {
			try {
				clienteService.saveCliente(cliente);
				model.addAttribute("msg", "Cliente gravado com sucesso!");
			} catch (ValidationException e) {
				// TODO Auto-generated catch block
				erros.rejectValue("email",null, e.getMessage());
			}
			
		}
		
		
		ControllerHelper.setEditMode(model, false);
		
		return "cliente-cadastro";
		
	}
	
	@GetMapping("/restaurante/new")
	public String newRestaurante(Model model) {
		
		model.addAttribute("restaurante", new Restaurante());
		ControllerHelper.setEditMode(model, false);		
		ControllerHelper.addCategoriasToRequest(categoriaRestauranteRepository, model);
		return "restaurante-cadastro";
	}
	
	@PostMapping(path = "/restaurante/save")
	public String saveRestaurante(@ModelAttribute("restaurante") @Valid Restaurante restaurante, 
			Errors erros, Model model) {
			
		if(!erros.hasErrors()) {
			try {
				
				restauranteService.saveRestaurante(restaurante);
				model.addAttribute("msg", "Restaurante gravado com sucesso!");
			} catch (ValidationException e) {
				// TODO Auto-generated catch block
				erros.rejectValue("email",null, e.getMessage());
				return "redirect:/public/restaurante/new";
			}
			
		}
		
		
		ControllerHelper.setEditMode(model, false);
		ControllerHelper.addCategoriasToRequest(categoriaRestauranteRepository, model);
		return "restaurante-cadastro";
		
	}

}

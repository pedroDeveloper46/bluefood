package pedrovictor.bluefood.infrastructure.web.controller;

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

import pedrovictor.bluefood.application.ClienteService;
import pedrovictor.bluefood.application.RestauranteService;
import pedrovictor.bluefood.application.ValidationException;
import pedrovictor.bluefood.domain.cliente.Cliente;
import pedrovictor.bluefood.domain.restaurante.Restaurante;

@Controller
@RequestMapping(path="/public")
public class PublicController {
	
	@Autowired
	private ClienteService clienteService;
	
	private RestauranteService restauranteService;
	
	
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
	public String newRestaurante() {
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
			}
			
		}
		
		
		ControllerHelper.setEditMode(model, false);
		return "restaurante-cadastro.html";
		
	}

}

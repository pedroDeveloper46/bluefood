package pedrovictor.bluefood.infrastructure.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pedrovictor.bluefood.application.ClienteService;
import pedrovictor.bluefood.domain.cliente.Cliente;

@Controller
@RequestMapping(path="/public")
public class PublicController {
	
	@Autowired
	private ClienteService clienteService;
	
	
	@GetMapping("/cliente/new")
	public String newCliente(Model model) {
		
		model.addAttribute("cliente", new Cliente());
		return "cliente-cadastro";
		
	}
	
	@PostMapping(path = "/cliente/save")
	public String saveCliente(@ModelAttribute("cliente") Cliente cliente) {
		
		clienteService.saveCliente(cliente);
		
		return "login";
		
	}
	
	@GetMapping("/restaurante/new")
	public String newRestaurante() {
		return "restaurante-cadastro";
	}

}

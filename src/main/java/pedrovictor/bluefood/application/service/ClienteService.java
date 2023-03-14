package pedrovictor.bluefood.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pedrovictor.bluefood.domain.cliente.Cliente;
import pedrovictor.bluefood.domain.cliente.ClienteRepository;
import pedrovictor.bluefood.domain.restaurante.Restaurante;
import pedrovictor.bluefood.domain.restaurante.RestauranteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Transactional
	public void saveCliente(Cliente cliente) throws ValidationException {
		
		cliente.setCep(cliente.getCep().replace("-", ""));
		
		if(!validateEmail(cliente.getEmail(), cliente.getId())) {
			throw new ValidationException("Esse e-mail já existe");
		}
		
		if(cliente.getId() != null) {
			
			Cliente clienteDB = clienteRepository.findById(cliente.getId()).orElseThrow();
			
			cliente.setSenha(clienteDB.getSenha());
		}
		else {
			cliente.encryptPassword();
		}
		 
		clienteRepository.save(cliente);
	}
	
	private boolean validateEmail(String email, Integer id) {
		
		Restaurante restaurante = restauranteRepository.findByEmail(email);
		
		if (restaurante != null) {
			return false;
		}
		
		Cliente cliente = clienteRepository.findByEmail(email);
		
		if(cliente != null) {
		
			if(id == null) {
				return false;
			}
			
			if(!cliente.getId().equals(id)) {
				return false;
			}
		}
		
		return true;
	
	}
	
	
	
	
	
	

}

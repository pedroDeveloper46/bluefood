package pedrovictor.bluefood.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pedrovictor.bluefood.domain.cliente.Cliente;
import pedrovictor.bluefood.domain.cliente.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public void saveCliente(Cliente cliente) throws ValidationException {
		
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

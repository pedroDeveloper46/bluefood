package pedrovictor.bluefood.application;

import org.springframework.beans.factory.annotation.Autowired;

import pedrovictor.bluefood.domain.restaurante.Restaurante;
import pedrovictor.bluefood.domain.restaurante.RestauranteRepository;

public class RestauranteService {
	
	@Autowired
	private RestauranteRepository RestauranteRepository;
	
	public void saveRestaurante(Restaurante restaurante) throws ValidationException {
		
		if(!validateEmail(restaurante.getEmail(), restaurante.getId())) {
			throw new ValidationException("Esse e-mail já existe");
		}
		
		/*if(restaurante.getId() != null) {
			
			Restaurante RestauranteDB = RestauranteRepository.findById(restaurante.getId()).orElseThrow();
			
			restaurante.setSenha(RestauranteDB.getSenha());
		}
		else {
			restaurante.encryptPassword();
		}*/
		
		RestauranteRepository.save(restaurante);
	}
	
	private boolean validateEmail(String email, Integer id) {
		
		Restaurante restaurante = RestauranteRepository.findByEmail(email);
		
		if(restaurante != null) {
		
			if(id == null) {
				return false;
			}
			
			if(!restaurante.getId().equals(id)) {
				return false;
			}
		}
		
		return true;
	
	}


}

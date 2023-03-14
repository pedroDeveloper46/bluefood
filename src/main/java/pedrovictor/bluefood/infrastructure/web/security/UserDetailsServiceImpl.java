package pedrovictor.bluefood.infrastructure.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pedrovictor.bluefood.domain.cliente.ClienteRepository;
import pedrovictor.bluefood.domain.restaurante.RestauranteRepository;
import pedrovictor.bluefood.domain.usuario.Usuario;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usu = clienteRepository.findByEmail(username);
		
		if (usu == null) {
			
			usu = restauranteRepository.findByEmail(username);
			
			if (usu == null) {
				throw new UsernameNotFoundException("Usuário não encontrado");
			}
			
		}
		
		return new LoggedUser(usu);
		
	}

}

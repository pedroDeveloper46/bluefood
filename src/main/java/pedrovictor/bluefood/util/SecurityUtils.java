package pedrovictor.bluefood.util;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import pedrovictor.bluefood.domain.cliente.Cliente;
import pedrovictor.bluefood.domain.restaurante.Restaurante;
import pedrovictor.bluefood.infrastructure.web.security.LoggedUser;

public class SecurityUtils {

	public static LoggedUser loggedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication instanceof AnonymousAuthenticationToken) {
			return null;
		}

		return (LoggedUser) authentication.getPrincipal();
	}
	
	public static Cliente loggedCliente() {

		LoggedUser loggedUser = loggedUser();

		if (loggedUser == null) {
			throw new IllegalStateException("Não existe um usuário logado");
		}

		if (!(loggedUser.getUsuario() instanceof Cliente)) {
			throw new IllegalStateException("O usuário logado não é um cliente");
		}

		return (Cliente) loggedUser.getUsuario();

	}

	public static Restaurante loggedCRestaurante() {

		LoggedUser loggedUser = loggedUser();

		if (loggedUser == null) {
			throw new IllegalStateException("Não existe um usuário logado");
		}

		if (!(loggedUser.getUsuario() instanceof Restaurante)) {
			throw new IllegalStateException("O usuário logado não é um Restaurante");
		}

		return (Restaurante) loggedUser.getUsuario();

	}

}

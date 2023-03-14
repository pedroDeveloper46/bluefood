package pedrovictor.bluefood.application.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pedrovictor.bluefood.domain.restaurante.Restaurante;
import pedrovictor.bluefood.domain.restaurante.RestauranteComparator;
import pedrovictor.bluefood.domain.cliente.Cliente;
import pedrovictor.bluefood.domain.cliente.ClienteRepository;
import pedrovictor.bluefood.domain.restaurante.CategoriaRestaurante;
import pedrovictor.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import pedrovictor.bluefood.domain.restaurante.ItemCardapio;
import pedrovictor.bluefood.domain.restaurante.ItemCardapioRepository;
import pedrovictor.bluefood.domain.restaurante.RestauranteRepository;
import pedrovictor.bluefood.domain.restaurante.SearchFilter;
import pedrovictor.bluefood.domain.restaurante.SearchFilter.SearchType;
import pedrovictor.bluefood.util.SecurityUtils;

@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CategoriaRestauranteRepository categoriaRestauranteRepository;

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ItemCardapioRepository cardapioRepository;

	@Autowired
	private ImageService imageService;

	@Transactional
	public void saveRestaurante(Restaurante restaurante) throws ValidationException {

		if (!validateEmail(restaurante.getEmail(), restaurante.getId())) {
			throw new ValidationException("Esse e-mail já existe");
		}

		if (restaurante.getId() != null) {

			Restaurante restauranteDB = restauranteRepository.findById(restaurante.getId()).orElseThrow();

			restaurante.setSenha(restauranteDB.getSenha());
			restaurante.setLogotipo(restauranteDB.getLogotipo());
			
			restauranteRepository.save(restaurante);
			
		} else {
			restaurante.encryptPassword();
			restaurante = restauranteRepository.save(restaurante);
			restaurante.setLogotipoFileName();
			imageService.uploadLogotipo(restaurante.getLogotipoFile(), restaurante.getLogotipo());
		}

	}

	private boolean validateEmail(String email, Integer id) {

		Cliente cliente = clienteRepository.findByEmail(email);

		if (cliente != null) {
			return false;
		}

		Restaurante restaurante = restauranteRepository.findByEmail(email);

		if (restaurante != null) {

			if (id == null) {
				return false;
			}

			if (!restaurante.getId().equals(id)) {
				return false;
			}
		}

		return true;

	}

	public List<CategoriaRestaurante> getAllCategorias() {

		return categoriaRestauranteRepository.findAll();
	}

	public List<Restaurante> search(SearchFilter searchFilter) {

		List<Restaurante> restaurantes = new ArrayList<Restaurante>();

		if (searchFilter.getSearchType() == SearchType.Texto) {
			restaurantes = restauranteRepository.findByNomeIgnoreCaseContaining(searchFilter.getTexto());

		} else if (searchFilter.getSearchType() == SearchType.Categoria) {
			restaurantes = restauranteRepository.findByCategorias_Id(searchFilter.getCategoriaId());
		} else {
			throw new IllegalStateException(searchFilter.getSearchType().toString() + "não é um tipo válido");
		}

		Iterator<Restaurante> it = restaurantes.iterator();

		while (it.hasNext()) {

			Restaurante restaurante = it.next();

			double taxaEntrega = restaurante.getTaxaEntrega().doubleValue();

			if (searchFilter.isEntregaGratis() && taxaEntrega > 0
					|| !searchFilter.isEntregaGratis() && taxaEntrega == 0) {
				it.remove();
			}

		}
		
		RestauranteComparator comparator = new RestauranteComparator(searchFilter,SecurityUtils.loggedCliente().getCep());
		restaurantes.sort(comparator);

		return restaurantes;

	}
	
	public void updateSenha(Restaurante restaurante, String novaSenha) {
		
		restaurante.setSenha(novaSenha);
		restaurante.encryptPassword();
		restauranteRepository.save(restaurante);		
		
	}
	
	@Transactional
	public void saveItemCardapio(ItemCardapio itemCardapio) {
		
		itemCardapio = cardapioRepository.save(itemCardapio);
		itemCardapio.setImagemFileName();
		imageService.uploadComida(itemCardapio.getImagemFile(), itemCardapio.getImagem());
		
	}

}

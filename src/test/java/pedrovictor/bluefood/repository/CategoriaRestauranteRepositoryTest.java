package pedrovictor.bluefood.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import pedrovictor.bluefood.domain.restaurante.CategoriaRestaurante;
import pedrovictor.bluefood.domain.restaurante.CategoriaRestauranteRepository;

@DataJpaTest
@ActiveProfiles("test")
public class CategoriaRestauranteRepositoryTest {
	
	@Autowired
	private CategoriaRestauranteRepository categoriaRestauranteRepository;
	
	@Test
	public void testInsertAndDelete() throws Exception {
		
		assertThat(categoriaRestauranteRepository).isNotNull();
		
		CategoriaRestaurante cr = new CategoriaRestaurante();
		cr.setNome("Chinesa");
		cr.setImagem("chinesa.png");
		categoriaRestauranteRepository.saveAndFlush(cr);
		
		assertThat(cr.getId()).isNotNull();
		
		CategoriaRestaurante cr2 = categoriaRestauranteRepository.findById(cr.getId()).orElseThrow();
		
		assertThat(cr.getNome().equals(cr2.getNome()));
		
		assertThat(categoriaRestauranteRepository.findAll()).hasSize(7);
		
		categoriaRestauranteRepository.delete(cr);
		
		assertThat(categoriaRestauranteRepository.findAll()).hasSize(6);
		
		
	}

}

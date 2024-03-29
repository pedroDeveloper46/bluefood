package pedrovictor.bluefood.domain.restaurante;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface RestauranteRepository extends JpaRepository<Restaurante, Integer> {
	
	public Restaurante findByEmail(String res);
	
	public List<Restaurante> findByNomeIgnoreCaseContaining(String nome);
	
	public List<Restaurante> findByCategorias_Id(Integer id);
	


}

package pedrovictor.bluefood.domain.restaurante;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemCardapioRepository extends JpaRepository<ItemCardapio, Integer> {
	
	public List<ItemCardapio> findByRestaurante_IdOrderByNome(Integer id);
	
	public List<ItemCardapio> findByRestaurante_IdAndDestaqueOrderByNome(Integer id, boolean destaque);
	
	public List<ItemCardapio> findByRestaurante_IdAndDestaqueAndCategoriaOrderByNome(Integer id, boolean destaque, String categoria);
	
	@Query("SELECT DISTINCT ic.categoria from ItemCardapio ic WHERE ic.restaurante.id = ?1 ORDER BY ic.categoria")
	public List<String> findCategorias(Integer restauranteId);
	
	

}

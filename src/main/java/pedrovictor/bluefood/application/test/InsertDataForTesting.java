package pedrovictor.bluefood.application.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import pedrovictor.bluefood.application.service.RestauranteService;
import pedrovictor.bluefood.domain.cliente.Cliente;
import pedrovictor.bluefood.domain.cliente.ClienteRepository;
import pedrovictor.bluefood.domain.restaurante.ItemCardapio;
import pedrovictor.bluefood.domain.restaurante.ItemCardapioRepository;
import pedrovictor.bluefood.domain.restaurante.Restaurante;
import pedrovictor.bluefood.domain.restaurante.RestauranteRepository;
@Component
public class InsertDataForTesting {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ItemCardapioRepository itemCardapioRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private RestauranteService restauranteService;
	
	
	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
	    //addItensCardapio();
		//alteraSenhaRestaurantes();
	}
	
	
	private Cliente[] addClientes() {
		List<Cliente> clientes = new ArrayList<>();
		
		Cliente c = new Cliente();
		
		c.setNome("Pedro Victor");
		c.setCpf("51925521842");
		c.setEmail("pedro46tr@gmail.com");
		c.setTelefone("11932846948");
		c.setCep("05116000");
		c.setSenha("123456");
		clienteRepository.save(c);
		
		c = new Cliente();
		c.setNome("Júlia");
		c.setCpf("12055398010");
		c.setEmail("julia@gmail.com");
		c.setTelefone("11981581034");
		c.setCep("06462080");
		c.setSenha("123456");
		clienteRepository.save(c);
		
		Cliente[] array = new Cliente[clientes.size()];
		return clientes.toArray(array); 
	}
	
	private void addItensCardapio() {
		
		ItemCardapio it = new ItemCardapio();
		
		Restaurante r = restauranteRepository.findById(6).orElseThrow();
		
		
		
		it.setNome("X-Salada");
		it.setDescricao("Alface,cebola,tomate");
		it.setCategoria("Sanduiche");
		it.setDestaque(false);
		it.setPreco(BigDecimal.valueOf(25.00));
		it.setImagem("0001-comida.png");
		it.setRestaurante(r);
		itemCardapioRepository.save(it);
		it.setImagemFileName();
//		
//		r = new Restaurante();
//		
//		r = restauranteRepository.findById(6).orElseThrow();
//		
//		it = new ItemCardapio();
//		it.setNome("Pizza de calabresa");
//		it.setDescricao("Pizza de calabresa");
//		it.setCategoria("Pizza");
//		it.setDestaque(true);
//		it.setPreco(BigDecimal.valueOf(32.00));
//		it.setImagem("0002-comida.png");
//		it.setRestaurante(r);
//		itemCardapioRepository.save(it);
//		it.setImagemFileName();
//		
//		
//		it = new ItemCardapio();
//		it.setNome("Pizza de Portuguesa");
//		it.setDescricao("Pizza de Portuguesa");
//		it.setCategoria("Pizza");
//		it.setDestaque(true);
//		it.setPreco(BigDecimal.valueOf(30.00));
//		it.setImagem("0003-comida.png");
//		it.setRestaurante(r);
//		itemCardapioRepository.save(it);
//		it.setImagemFileName();
		
//		it = new ItemCardapio();
//		it.setNome("Refrigerante");
//		it.setDescricao("Refrigerantes 0% açucar");
//		it.setCategoria("Refrigerante");
//		it.setDestaque(false);
//		it.setPreco(BigDecimal.valueOf(10.00));
//		it.setImagem("0005-comida.jpg");
//		it.setRestaurante(r);
//		itemCardapioRepository.save(it);
//		it.setImagemFileName();
//		
//		
//		it = new ItemCardapio();
//		it.setNome("Pizza de Queijo");
//		it.setDescricao("queijo e tomate ");
//		it.setCategoria("Pizza");
//		it.setDestaque(false);
//		it.setPreco(BigDecimal.valueOf(32.00));
//		it.setImagem("0004-comida.jpg");
//		it.setRestaurante(r);
//		itemCardapioRepository.save(it);
//		it.setImagemFileName();
		
		
		
	}
	
	public void alteraSenhaRestaurantes() {
		
		Restaurante r = restauranteRepository.findById(1).orElseThrow();
		
		restauranteService.updateSenha(r, "123");
		
		r = restauranteRepository.findById(2).orElseThrow();
		
		restauranteService.updateSenha(r, "1234");
		
		r = restauranteRepository.findById(3).orElseThrow();
		
		restauranteService.updateSenha(r,"churrasco");
		
		r = restauranteRepository.findById(4).orElseThrow();
		
		restauranteService.updateSenha(r, "1234567890");
		
		r = restauranteRepository.findById(5).orElseThrow();
		
		restauranteService.updateSenha(r, "123456");
		
	}

}

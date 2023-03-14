package pedrovictor.bluefood.application.service;

import java.time.LocalDateTime;

import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import pedrovictor.bluefood.domain.pagamento.DadosCartao;
import pedrovictor.bluefood.domain.pagamento.Pagamento;
import pedrovictor.bluefood.domain.pagamento.PagamentoRepository;
import pedrovictor.bluefood.domain.pagamento.StatusPagamento;
import pedrovictor.bluefood.domain.pedido.Carrinho;
import pedrovictor.bluefood.domain.pedido.ItemPedido;
import pedrovictor.bluefood.domain.pedido.ItemPedidoPK;
import pedrovictor.bluefood.domain.pedido.ItemPedidoRepository;
import pedrovictor.bluefood.domain.pedido.Pedido;
import pedrovictor.bluefood.domain.pedido.Pedido.Status;
import pedrovictor.bluefood.domain.pedido.PedidoRepository;
import pedrovictor.bluefood.util.SecurityUtils;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Value("${bluefood.sbpay.url}")
	private String sbPayUrl;
	
	@Value("${bluefood.sbpay.token}")
	private String sbPayToken;

	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = PagamentoException.class)
	public Pedido criaEPagar(Carrinho carrinho, String numCartao) throws PagamentoException {
		
		Pedido pedido = new Pedido();
		pedido.setData(LocalDateTime.now());
		pedido.setCliente(SecurityUtils.loggedCliente());
		pedido.setRestaurante(carrinho.getRestaurante());
		pedido.setTaxaEntrega(carrinho.getRestaurante().getTaxaEntrega());
		pedido.setSubtotal(carrinho.getPrecoTotal(false));
		pedido.setTotal(carrinho.getPrecoTotal(true));
		pedido.setStatus(Status.Producao);
		
		pedido = pedidoRepository.save(pedido);
		
		int ordem = 1;
		
		for (ItemPedido itemPedido : carrinho.getItens()) {
			
			itemPedido.setId(new ItemPedidoPK(pedido, ordem++));
			itemPedidoRepository.save(itemPedido);
		}
		
		DadosCartao dadosCartao = new DadosCartao();
		dadosCartao.setNumCartao(numCartao);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		
		headers.add("Token", sbPayToken);
		
		HttpEntity<DadosCartao> requestEntity = new HttpEntity<>(dadosCartao, headers);
		
		RestTemplate restTemplate = new RestTemplate();
		
		Map<String, String> response;
		
		try {
		     response = restTemplate.postForObject(sbPayUrl, requestEntity, Map.class);
					
		} catch (Exception e) {
			throw new PagamentoException("Erro no Servidor de Pagamento");
		}
		
		StatusPagamento status = StatusPagamento.valueOf(response.get("status"));
		
		if (!status.equals(StatusPagamento.Autorizado)) {
			throw new PagamentoException(status.getDescricao());
		}
		
		Pagamento pagamento = new Pagamento();
		pagamento.setData(LocalDateTime.now());
		pagamento.setPedido(pedido);
		pagamento.definirNumeroEBandeiro(numCartao);
		pagamentoRepository.save(pagamento);
		
		
		return pedido;
		
		
	}
	
}

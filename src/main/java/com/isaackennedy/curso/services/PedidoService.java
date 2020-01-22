package com.isaackennedy.curso.services;

import java.util.Date;
import java.util.Optional;

import com.isaackennedy.curso.domain.ItemPedido;
import com.isaackennedy.curso.domain.PagamentoComBoleto;
import com.isaackennedy.curso.domain.Produto;
import com.isaackennedy.curso.domain.enums.EstadoPagamento;
import com.isaackennedy.curso.repositories.ItemPedidoRepository;
import com.isaackennedy.curso.repositories.PagamentoRepository;
import com.isaackennedy.curso.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isaackennedy.curso.domain.Pedido;
import com.isaackennedy.curso.repositories.PedidoRepository;
import com.isaackennedy.curso.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	ItemPedidoRepository ipRepository;
	
	public Pedido find(Integer id) {
		Optional<Pedido> cat = repo.findById(id);
		
		return cat.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pgto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPgtoComBoleto(pgto, obj.getInstante());
		}

		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoRepository.findById(ip.getProduto().getId()).get().getPreco());
			ip.setPedido(obj);
		}
		ipRepository.saveAll(obj.getItens());
		return obj;
	}
	
}

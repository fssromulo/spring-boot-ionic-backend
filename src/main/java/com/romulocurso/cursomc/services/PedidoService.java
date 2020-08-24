package com.romulocurso.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.romulocurso.cursomc.domain.ItemPedido;
import com.romulocurso.cursomc.domain.PagamentoComBoleto;
import com.romulocurso.cursomc.domain.Pedido;
import com.romulocurso.cursomc.domain.Produto;
import com.romulocurso.cursomc.domain.enums.EstadoPagamento;
import com.romulocurso.cursomc.repositories.ItemPedidoRepository;
import com.romulocurso.cursomc.repositories.PagamentoRepository;
import com.romulocurso.cursomc.repositories.PedidoRepository;
import com.romulocurso.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ProdutoService produtoService;
	
	public Pedido find(Integer idPedido) {
		Optional<Pedido> obj = repo.findById(idPedido);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + idPedido + ", Tipo: " + Pedido.class.getName()));
	}

	@Transactional
	public Pedido insert(Pedido obj) {

		obj.setId(null);
		obj.setInstante(new Date());

		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);

		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagtoBoleto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagtoBoleto, obj.getInstante());
		}

		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());

		for (ItemPedido item : obj.getItens()) {
			item.setDesconto(0.0);		
			Produto produtoSalvar = produtoService.find(item.getProduto().getId());
			item.setPreco(produtoSalvar.getPreco());
			item.setPedido(obj);
		}
		
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;

	}
}

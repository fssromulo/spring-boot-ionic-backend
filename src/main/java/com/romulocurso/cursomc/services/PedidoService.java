package com.romulocurso.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romulocurso.cursomc.domain.Pedido;
import com.romulocurso.cursomc.repositories.PedidoRepository;
import com.romulocurso.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	public Pedido buscar(Integer idPedido) {
		Optional<Pedido> obj = repo.findById(idPedido); 		
		return obj.orElseThrow( () -> 
			new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + idPedido + ", Tipo: " + Pedido.class.getName()
			)
		);
	}
	
}

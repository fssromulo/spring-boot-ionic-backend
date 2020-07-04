package com.romulocurso.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romulocurso.cursomc.domain.Cliente;
import com.romulocurso.cursomc.repositories.ClienteRepository;
import com.romulocurso.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public Cliente buscar(Integer idCliente) {
		Optional<Cliente> obj = repo.findById(idCliente); 		
		return obj.orElseThrow( () -> 
			new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + idCliente + ", Tipo: " + Cliente.class.getName()
			)
		);
	}
	
}

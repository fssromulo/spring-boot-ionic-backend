package com.romulocurso.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romulocurso.cursomc.domain.Categoria;
import com.romulocurso.cursomc.repositories.CategoriaRepository;
import com.romulocurso.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer idCategoria) {
		Optional<Categoria> obj = repo.findById(idCategoria); 		
		return obj.orElseThrow( () -> 
			new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + idCategoria + ", Tipo: " + Categoria.class.getName()
			)
		);
	}
	
}

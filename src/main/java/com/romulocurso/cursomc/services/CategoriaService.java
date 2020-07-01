package com.romulocurso.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romulocurso.cursomc.domain.Categoria;
import com.romulocurso.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer idCategoria) { 
		return repo.findById(idCategoria).orElse(null);
	}
	
}

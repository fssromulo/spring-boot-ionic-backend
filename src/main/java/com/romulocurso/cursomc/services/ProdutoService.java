package com.romulocurso.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.romulocurso.cursomc.domain.Categoria;
import com.romulocurso.cursomc.domain.Pedido;
import com.romulocurso.cursomc.domain.Produto;
import com.romulocurso.cursomc.repositories.CategoriaRepository;
import com.romulocurso.cursomc.repositories.ProdutoRepository;
import com.romulocurso.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository caterigoriaRepository;
	
	public Produto find(Integer idPedido) {
		Optional<Produto> obj = repo.findById(idPedido); 		
		return obj.orElseThrow( () -> 
			new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + idPedido + ", Tipo: " + Pedido.class.getName()
			)
		);
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
		List<Categoria> categorias = caterigoriaRepository.findAllById(ids);
		// return repo.search(nome, categorias, pageRequest);
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
		
	}
	
}

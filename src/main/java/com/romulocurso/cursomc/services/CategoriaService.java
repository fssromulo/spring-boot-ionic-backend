package com.romulocurso.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.romulocurso.cursomc.domain.Categoria;
import com.romulocurso.cursomc.domain.Cliente;
import com.romulocurso.cursomc.dto.CategoriaDTO;
import com.romulocurso.cursomc.repositories.CategoriaRepository;
import com.romulocurso.cursomc.services.exceptions.DataIntegrityException;
import com.romulocurso.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer idCategoria) {
		Optional<Categoria> obj = repo.findById(idCategoria); 		
		return obj.orElseThrow( () -> 
			new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + idCategoria + ", Tipo: " + Categoria.class.getName()
			)
		);
	}
	
	public List<Categoria> findAll() {
		return repo.findAll();
	}
	
	public Categoria insert(Categoria obj ) {
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {		
		Categoria newObj = this.find(obj.getId());
		this.updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		this.find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos vinculados.");
		}
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
		return repo.findAll(pageRequest);
		
	}
	
	public Categoria fromDTO(CategoriaDTO objDTO) {
		return new Categoria(
			objDTO.getId(),
			objDTO.getNome()
		);			
	}
	
	private void updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());		
	}	
}

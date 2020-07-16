package com.romulocurso.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.romulocurso.cursomc.domain.Categoria;
import com.romulocurso.cursomc.dto.CategoriaDTO;
import com.romulocurso.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> findAll() { 					
		List<Categoria> arrCategoria = service.findAll();
		
		List<CategoriaDTO> arrCategoriaDTO = arrCategoria.stream().map(
				categoria -> new CategoriaDTO(categoria)
		).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(arrCategoriaDTO);
	}
	
	@GetMapping(value="/page")
	public ResponseEntity<Page<CategoriaDTO>> findPage(
		@RequestParam(value="page", defaultValue="0")  Integer page, 
		@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
		@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
		@RequestParam(value="direction", defaultValue="ASC") String direction				
	) { 					
		Page<Categoria> arrCategoria = service.findPage(page, linesPerPage, orderBy, direction);
		
		Page<CategoriaDTO> arrCategoriaDTO = arrCategoria.map(
			categoria -> new CategoriaDTO(categoria)
		);
		
		return ResponseEntity.ok().body(arrCategoriaDTO);
	}
	
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Categoria> find(@PathVariable Integer id) { 					
		Categoria cat1 = service.find(id);
		return ResponseEntity.ok().body(cat1);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDTO) {
		Categoria obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return  ResponseEntity.created(uri).build();			
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Void> update(
		@PathVariable Integer id,
		@Valid @RequestBody CategoriaDTO objDTO
	) {
		Categoria obj = service.fromDTO(objDTO);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return 	ResponseEntity.noContent().build();
	}
	
}

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.romulocurso.cursomc.domain.Cliente;
import com.romulocurso.cursomc.domain.Cliente;
import com.romulocurso.cursomc.dto.ClienteDTO;
import com.romulocurso.cursomc.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;

	@GetMapping(value="/{id}")
	public ResponseEntity<Cliente> find(@PathVariable Integer id) { 					
		Cliente cliente = service.find(id);
		return ResponseEntity.ok().body(cliente);
	}
	
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll() { 					
		List<Cliente> arrCliente = service.findAll();
		
		List<ClienteDTO> arrClienteDTO = arrCliente.stream().map(
				Cliente -> new ClienteDTO(Cliente)
		).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(arrClienteDTO);
	}
	
	@GetMapping(value="/page")
	public ResponseEntity<Page<ClienteDTO>> findPage(
		@RequestParam(value="page", defaultValue="0")  Integer page, 
		@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
		@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
		@RequestParam(value="direction", defaultValue="ASC") String direction				
	) { 					
		Page<Cliente> arrCliente = service.findPage(page, linesPerPage, orderBy, direction);
		
		Page<ClienteDTO> arrClienteDTO = arrCliente.map(
			Cliente -> new ClienteDTO(Cliente)
		);
		
		return ResponseEntity.ok().body(arrClienteDTO);
	}
		
	@PutMapping(value="/{id}")
	public ResponseEntity<Void> update(
		@PathVariable Integer id,
		@Valid @RequestBody ClienteDTO objDTO
	) {
		Cliente obj = service.fromDTO(objDTO);
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

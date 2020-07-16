package com.romulocurso.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.romulocurso.cursomc.domain.Cliente;
import com.romulocurso.cursomc.domain.Cliente;
import com.romulocurso.cursomc.dto.ClienteDTO;
import com.romulocurso.cursomc.repositories.ClienteRepository;
import com.romulocurso.cursomc.services.exceptions.DataIntegrityException;
import com.romulocurso.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Integer idCliente) {
		Optional<Cliente> obj = repo.findById(idCliente); 		
		return obj.orElseThrow( () -> 
			new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + idCliente + ", Tipo: " + Cliente.class.getName()
			)
		);
	}
	
	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	public Cliente insert(Cliente obj ) {
		return repo.save(obj);
	}
	
	public Cliente update(Cliente obj) {
		Cliente objAlterar = this.find(obj.getId());
		
		updateData(objAlterar, obj);
		return repo.save(objAlterar);
	}
	
	public void delete(Integer id) {
		this.find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um cliente que possui pedidos.");
		}
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
		return repo.findAll(pageRequest);
		
	}
	
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(
			objDTO.getId(),
			objDTO.getNome(),
			objDTO.getEmail(),
			null,
			null
		);
	}
	
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());		
	}
	
}

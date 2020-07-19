package com.romulocurso.cursomc.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.romulocurso.cursomc.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	/*
		A anotação @Repository junto com o interface JpaRepository 
	 	Permite fazer as operações de CRUD do objeto Cliente que está mapeando o banco de dados 	 
	*/
	
	@Transactional(readOnly=true)
	Cliente findByEmail(String email);
}

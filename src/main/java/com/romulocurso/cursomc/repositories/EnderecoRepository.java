package com.romulocurso.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.romulocurso.cursomc.domain.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
	/*
		A anotação @Repository junto com o interface JpaRepository 
	 	Permite fazer as operações de CRUD do objeto Categoria que está mapeando o banco de dados 	 
	*/
}

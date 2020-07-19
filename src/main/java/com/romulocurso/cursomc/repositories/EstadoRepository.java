package com.romulocurso.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.romulocurso.cursomc.domain.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {
	/*
		A anotação @Repository junto com o interface JpaRepository 
	 	Permite fazer as operações de CRUD do objeto Categoria que está mapeando o banco de dados 	 
	*/
}

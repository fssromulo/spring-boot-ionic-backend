package com.romulocurso.cursomc.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.romulocurso.cursomc.domain.Cliente;

import lombok.Data;

@Data
public class ClienteDTO {

	private Integer id;
	
	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min=5, max=120, message="O nome deve conter entre 5 e 120 caracteres")
	private String nome;
	
	@NotEmpty(message="Preenchimento obrigatório")
	@Email
	private String email;

	public ClienteDTO() {
		
	}
	
	public ClienteDTO(Cliente obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.email = obj.getEmail();
	} 
	
}

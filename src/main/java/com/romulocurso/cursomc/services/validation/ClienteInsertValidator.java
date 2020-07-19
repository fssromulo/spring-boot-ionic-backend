package com.romulocurso.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.romulocurso.cursomc.domain.enums.TipoCliente;
import com.romulocurso.cursomc.dto.ClienteNewDTO;
import com.romulocurso.cursomc.resources.exception.FieldMessage;
import com.romulocurso.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		// inclua os testes aqui, inserindo erros na lista
		
		if (objDto.getTipo() == null) {
			list.add(new FieldMessage("Tipo", "Tipo não pode ser nulo"));
		}

		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCpnj())) {
			list.add(new FieldMessage("cpfOuCpnj", "CPF inválido"));
		}

		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCpnj())) {
			list.add(new FieldMessage("cpfOuCpnj", "CNPJ inválido"));
		}

		for (FieldMessage e : list) {
			// Pegando cada erro do fieldMessage para adicionar na lista de erros do Spring
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
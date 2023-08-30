package com.ethos.empresaapi.controller.request;

import jakarta.validation.constraints.Email;
import lombok.NonNull;
import org.hibernate.validator.constraints.br.CNPJ;

public record EmpresaRequest(String razaoSocial, @CNPJ(message = "Cnpj inválido") String cnpj, String telefone, @Email String email, String senha, String setor, Integer quantidadeFuncionarios) {
}

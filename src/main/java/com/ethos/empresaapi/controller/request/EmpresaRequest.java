package com.ethos.empresaapi.controller.request;

import lombok.NonNull;
import org.hibernate.validator.constraints.br.CNPJ;

public record EmpresaRequest(String nome, @CNPJ(message = "Cnpj inválido") String cnpj, String telefone) {
}

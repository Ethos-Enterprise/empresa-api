package com.ethos.empresaapi.controller.request;

import org.hibernate.validator.constraints.br.CNPJ;

public record EmpresaRequest(String nome, @CNPJ String cnpj, String telefone) {
}

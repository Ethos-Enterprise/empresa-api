package com.ethos.empresaapi.controller.response;

import java.util.UUID;

public record EmpresaResponse(UUID id, String razaoSocial, String cnpj, String telefone, String email, String setor, Integer qtdFuncionarios) {
}

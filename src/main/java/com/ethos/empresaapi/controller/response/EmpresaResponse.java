package com.ethos.empresaapi.controller.response;

import java.util.UUID;

public record EmpresaResponse(UUID id, String nome, String cnpj, String telefone) {
}

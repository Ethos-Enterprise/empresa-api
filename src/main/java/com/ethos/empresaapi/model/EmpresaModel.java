package com.ethos.empresaapi.model;

import lombok.Builder;

public record EmpresaModel(
    String nome,
    String cnpj,
    String telefone
) {
    @Builder(toBuilder = true)
    public EmpresaModel(String nome, String cnpj, String telefone) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.telefone = telefone;
    }
}

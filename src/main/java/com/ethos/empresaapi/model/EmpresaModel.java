package com.ethos.empresaapi.model;

import lombok.Builder;

public record EmpresaModel(String nome, String cnpj, String telefone, String email, String senha, String setor, Integer quantidadeFuncionarios) {
    @Builder(toBuilder = true)
    public EmpresaModel(String nome, String cnpj, String telefone, String email, String senha, String setor, Integer quantidadeFuncionarios) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.setor = setor;
        this.quantidadeFuncionarios = quantidadeFuncionarios;
    }
}

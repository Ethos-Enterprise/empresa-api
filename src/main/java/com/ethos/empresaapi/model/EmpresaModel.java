package com.ethos.empresaapi.model;

import lombok.Builder;

public record EmpresaModel(String razaoSocial, String cnpj, String telefone, String email, String senha, String setor, Integer qtdFuncionarios) {
    @Builder(toBuilder = true)
    public EmpresaModel(String razaoSocial, String cnpj, String telefone, String email, String senha, String setor, Integer qtdFuncionarios) {
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.setor = setor;
        this.qtdFuncionarios = qtdFuncionarios;
    }
}

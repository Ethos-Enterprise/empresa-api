package com.ethos.empresaapi.model;

import lombok.Builder;

public record Endereco(
        String cep,
        String numero,
        String logradouro,
        String complemento,
        String bairro,
        String uf,
        Empresa empresa
) {
    @Builder(toBuilder = true)
    public Endereco(String cep, String numero, String logradouro, String complemento, String bairro, String uf, Empresa empresa) {
        this.cep = cep;
        this.numero = numero;
        this.logradouro = logradouro;
        this.complemento = complemento;
        this.bairro = bairro;
        this.uf = uf;
        this.empresa = empresa;
    }

    @Override
    public String cep() {
        return cep;
    }

    @Override
    public String logradouro() {
        return logradouro;
    }

    @Override
    public String complemento() {
        return complemento;
    }

    @Override
    public String bairro() {
        return bairro;
    }

    @Override
    public String uf() {
        return uf;
    }
}

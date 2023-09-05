package com.ethos.empresaapi.model;

import com.ethos.empresaapi.api.addressdto.AddressViaCep;
import lombok.Builder;

public record Empresa(String razaoSocial, String cnpj, String telefone, String email, String senha, String setor, Endereco endereco,
                      Integer qtdFuncionarios) {
    @Builder(toBuilder = true)
    public Empresa(String razaoSocial, String cnpj, String telefone, String email, String senha, String setor, Endereco endereco,
                   Integer qtdFuncionarios) {
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.setor = setor;
        this.endereco = endereco;
        this.qtdFuncionarios = qtdFuncionarios;
    }

    public Empresa updateEnderecoFrom(AddressViaCep addressViaCep) {
        if(addressViaCep == null){
            return this;
        }
        return this.toBuilder().endereco(Endereco.builder().uf(addressViaCep.uf()).cep(this.endereco.cep()).bairro(addressViaCep.bairro())
                .complemento(this.endereco.complemento()).logradouro(addressViaCep.logradouro()).numero(this.endereco.numero()).build()
        ).build();
    }
}

package com.ethos.empresaapi.api.addressdto;

public class AddressViaCepBuilder {
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String uf;

    public AddressViaCepBuilder setCep(String cep) {
        this.cep = cep;
        return this;
    }

    public AddressViaCepBuilder setLogradouro(String logradouro) {
        this.logradouro = logradouro;
        return this;
    }

    public AddressViaCepBuilder setComplemento(String complemento) {
        this.complemento = complemento;
        return this;
    }

    public AddressViaCepBuilder setBairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public AddressViaCepBuilder setUf(String uf) {
        this.uf = uf;
        return this;
    }

    public AddressViaCep createAddressViaCep() {
        return new AddressViaCep(cep, logradouro, complemento, bairro, uf);
    }
}

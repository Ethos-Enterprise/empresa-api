package com.ethos.empresaapi.api.addressdto;

public record AddressViaCep(
        String cep,
        String logradouro,
        String complemento,
        String bairro,
        String uf) {
}

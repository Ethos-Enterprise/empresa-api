package com.ethos.empresaapi.api;

import com.ethos.empresaapi.api.addressdto.AddressViaCep;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws/")
public interface ViaCepApiClient {
    @GetMapping(path = "/{cep}/json/")
    AddressViaCep getAddress(@PathVariable(value = "cep") String cep);
}

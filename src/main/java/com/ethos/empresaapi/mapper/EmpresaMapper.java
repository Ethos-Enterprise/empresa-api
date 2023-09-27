package com.ethos.empresaapi.mapper;

import com.ethos.empresaapi.controller.request.EmpresaRequest;
import com.ethos.empresaapi.model.Empresa;
import com.ethos.empresaapi.model.Endereco;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", nullValueMappingStrategy = org.mapstruct.NullValueMappingStrategy.RETURN_DEFAULT)
public interface EmpresaMapper {
    @Mapping(target = "endereco", source = "endereco")
    Empresa from(EmpresaRequest empresaRequest);
    @Mapping(target = "logradouro", ignore = true)
    @Mapping(target = "bairro", ignore = true)
    @Mapping(target = "uf", ignore = true)
    Endereco from(EmpresaRequest.EnderecoRequest enderecoRequest);
}

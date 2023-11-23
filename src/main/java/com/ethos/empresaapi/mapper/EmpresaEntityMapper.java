package com.ethos.empresaapi.mapper;

import com.ethos.empresaapi.model.Empresa;
import com.ethos.empresaapi.model.Endereco;
import com.ethos.empresaapi.repository.entity.EmpresaEntity;
import com.ethos.empresaapi.repository.entity.EnderecoEntity;
import com.ethos.empresaapi.repository.entity.PlanoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", nullValueMappingStrategy = org.mapstruct.NullValueMappingStrategy.RETURN_DEFAULT)
public interface EmpresaEntityMapper {

    @Mapping(source = "plano", target = "plano", ignore = true)
    EmpresaEntity from(Empresa empresa);

    EnderecoEntity from(Endereco endereco);
}

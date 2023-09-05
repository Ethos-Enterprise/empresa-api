package com.ethos.empresaapi.mapper;

import com.ethos.empresaapi.model.Empresa;
import com.ethos.empresaapi.model.Endereco;
import com.ethos.empresaapi.repository.entity.EmpresaEntity;
import com.ethos.empresaapi.repository.entity.EnderecoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", nullValueMappingStrategy = org.mapstruct.NullValueMappingStrategy.RETURN_DEFAULT)
public interface EmpresaEntityMapper {
    EmpresaEntity from(Empresa empresa);

    EnderecoEntity from(Endereco endereco);
}

package com.ethos.empresaapi.mapper;

import com.ethos.empresaapi.controller.response.EmpresaResponse;
import com.ethos.empresaapi.repository.entity.EmpresaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", nullValueMappingStrategy = org.mapstruct.NullValueMappingStrategy.RETURN_DEFAULT)
public interface EmpresaResponseMapper {
    EmpresaResponse from(EmpresaEntity empresaEntity);
}

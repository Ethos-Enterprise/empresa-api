package com.ethos.empresaapi.mapper;

import com.ethos.empresaapi.model.EmpresaModel;
import com.ethos.empresaapi.repository.entity.EmpresaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", nullValueMappingStrategy = org.mapstruct.NullValueMappingStrategy.RETURN_DEFAULT)
public interface EmpresaEntityMapper {
    EmpresaEntity from(EmpresaModel empresaModel);
}

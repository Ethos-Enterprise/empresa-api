package com.ethos.empresaapi.mapper;

import com.ethos.empresaapi.controller.request.EmpresaRequest;
import com.ethos.empresaapi.model.EmpresaModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", nullValueMappingStrategy = org.mapstruct.NullValueMappingStrategy.RETURN_DEFAULT)
public interface EmpresaMapper {
    EmpresaModel from(EmpresaRequest empresaRequest);
}

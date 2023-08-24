package com.ethos.empresaapi.service;

import com.ethos.empresaapi.controller.request.EmpresaRequest;
import com.ethos.empresaapi.controller.response.EmpresaResponse;
import com.ethos.empresaapi.exception.EmpresaJaExisteException;
import com.ethos.empresaapi.exception.EmpresaNaoExisteException;
import com.ethos.empresaapi.mapper.EmpresaEntityMapper;
import com.ethos.empresaapi.mapper.EmpresaMapper;
import com.ethos.empresaapi.mapper.EmpresaResponseMapper;
import com.ethos.empresaapi.model.EmpresaModel;
import com.ethos.empresaapi.repository.EmpresaRepository;
import com.ethos.empresaapi.repository.entity.EmpresaEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository repository;
    private final EmpresaMapper empresaModelMapper;
    private final EmpresaEntityMapper empresaEntityMapper;
    private final EmpresaResponseMapper empresaResponseMapper;

    public EmpresaResponse postEmpresa(EmpresaRequest request) {
        EmpresaModel model = empresaModelMapper.from(request);
        EmpresaEntity entity = empresaEntityMapper.from(model);
        EmpresaEntity savedEntity = saveEmpresa(entity);
        return empresaResponseMapper.from(savedEntity);
    }

    private EmpresaEntity saveEmpresa(EmpresaEntity entity) {
        EmpresaEntity empresaSaved;
        try {
            empresaSaved = repository.save(entity);
        } catch (DataIntegrityViolationException e) {
            throw new EmpresaJaExisteException("Empresa com o CNPJ %s já cadastrada".formatted(entity.getCnpj()));
        }
        return empresaSaved;
    }

    public List<EmpresaResponse> getAllEmpresa() {
        List<EmpresaEntity> empresas = (repository.findAll());
        return empresas.stream().map(empresaResponseMapper::from).toList();
    }

    public EmpresaResponse getEmpresaById(UUID id) {
        Optional<EmpresaEntity> empresa = repository.findById(id);
        if (empresa.isEmpty()) {
            throw new EmpresaNaoExisteException("Empresa com id %s não existe".formatted(id.toString()));
        }
        return empresa.map(empresaResponseMapper::from).get();
    }

    public EmpresaResponse putEmpresaById(UUID id, EmpresaRequest request) {
        Optional<EmpresaEntity> entity;
        entity = repository.findById(id);
        if (entity.isEmpty()) {
            throw new EmpresaNaoExisteException("Empresa com o id %s não existe".formatted(id.toString()));
        }
        repository.updateNome(request.nome(), id);
        repository.updateTelefone(request.telefone(), id);
        repository.updateCnpj(request.cnpj(), id);
        return entity.map(empresaResponseMapper::from).get();
    }

    public String deleteEmpresaById(UUID id) {
        try {
            repository.deleteById(id);
            return "Empresa deletada com sucesso";
        } catch (Exception e) {
            return "Empresa não encontrada";
        }
    }
}

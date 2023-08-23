package com.ethos.empresaapi.service;

import com.ethos.empresaapi.controller.request.EmpresaRequest;
import com.ethos.empresaapi.controller.response.EmpresaResponse;
import com.ethos.empresaapi.mapper.EmpresaEntityMapper;
import com.ethos.empresaapi.mapper.EmpresaMapper;
import com.ethos.empresaapi.mapper.EmpresaResponseMapper;
import com.ethos.empresaapi.model.EmpresaModel;
import com.ethos.empresaapi.repository.EmpresaRepository;
import com.ethos.empresaapi.repository.entity.EmpresaEntity;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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
        return repository.save(entity);
    }
    public List<EmpresaResponse> getAllEmpresa() {
        List<EmpresaEntity> empresas = (repository.findAll());

        return empresas.stream().map(empresaResponseMapper::from).toList();
    }

    public EmpresaResponse getEmpresaById(UUID id) {
        EmpresaEntity empresa = repository.findById(id).orElseThrow();
        return empresaResponseMapper.from(empresa);
    }

    public EmpresaResponse putEmpresaById(UUID id, EmpresaRequest request) {
        repository.updateNome(request.nome(), id);
        repository.updateTelefone(request.telefone(), id);
        repository.updateCnpj(request.cnpj(), id);
        EmpresaEntity entity = repository.findById(id).orElseThrow();
        return empresaResponseMapper.from(entity);
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

package com.ethos.empresaapi.service;

import com.ethos.empresaapi.api.ViaCepApiClient;
import com.ethos.empresaapi.api.addressdto.AddressViaCep;
import com.ethos.empresaapi.controller.request.EmpresaRequest;
import com.ethos.empresaapi.controller.response.EmpresaResponse;
import com.ethos.empresaapi.exception.EmpresaJaExisteException;
import com.ethos.empresaapi.exception.EmpresaNaoExisteException;
import com.ethos.empresaapi.exception.EnderecoNaoEncontradoException;
import com.ethos.empresaapi.mapper.EmpresaEntityMapper;
import com.ethos.empresaapi.mapper.EmpresaMapper;
import com.ethos.empresaapi.mapper.EmpresaResponseMapper;
import com.ethos.empresaapi.model.Empresa;
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

    private final ViaCepApiClient viaCepApiClient;
    private final EmpresaRepository repository;
    private final EmpresaMapper empresaModelMapper;
    private final EmpresaEntityMapper empresaEntityMapper;
    private final EmpresaResponseMapper empresaResponseMapper;

    public EmpresaResponse postEmpresa(EmpresaRequest request) {
        Empresa model = empresaModelMapper.from(request);
        final String cep = request.enderecoRequest() == null ? null : request.enderecoRequest().cep();
        AddressViaCep addressViaCep = getAddressViaCep(cep);
        Empresa modelAddressUpdated = model.updateEnderecoFrom(addressViaCep);
        EmpresaEntity entity = empresaEntityMapper.from(modelAddressUpdated);
        EmpresaEntity savedEntity = saveEmpresa(entity);
        return empresaResponseMapper.from(savedEntity);
    }

    private EmpresaEntity saveEmpresa(EmpresaEntity entity) {
        EmpresaEntity empresaSaved = null;
        try {
            empresaSaved = repository.save(entity);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("cnpj")) {
                throw new EmpresaJaExisteException("Empresa com o CNPJ %s já cadastrada".formatted(entity.getCnpj()));
            } else if (e.getMessage().contains("email")) {
                throw new EmpresaJaExisteException("Empresa com o email %s já cadastrada".formatted(entity.getEmail()));
            } else if (e.getMessage().contains("telefone")) {
                throw new EmpresaJaExisteException("Empresa com o telefone %s já cadastrada".formatted(entity.getTelefone()));
            }
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
            throw new EmpresaNaoExisteException("Empresa com o id %s não existe".formatted(id));
        }
        if (request.razaoSocial() != null && !request.razaoSocial().isEmpty()) {
            repository.updateNome(request.razaoSocial(), id);
        }
        if (request.telefone() != null && !request.telefone().isEmpty()) {
            repository.updateTelefone(request.telefone(), id);
        }
        if (request.cnpj() != null && !request.cnpj().isEmpty()) {
            repository.updateCnpj(request.cnpj(), id);
        }
        if (request.email() != null && !request.email().isEmpty()) {
            repository.updateEmail(request.email(), id);
        }
        if (request.senha() != null && !request.senha().isEmpty()) {
            repository.updateSenha(request.senha(), id);
        }
        if (request.setor() != null && !request.setor().isEmpty()) {
            repository.updateSetor(request.setor(), id);
        }
        if (request.qtdFuncionarios() != null) {
            repository.updateQtdFuncionarios(request.qtdFuncionarios(), id);
        }

        return repository.findById(id).map(empresaResponseMapper::from).get();
    }

    public String deleteEmpresaById(UUID id) {
        Optional<EmpresaEntity> empresa = repository.findById(id);
        if (empresa.isEmpty()) {
            throw new EmpresaNaoExisteException("Empresa com o id %s não existe".formatted(id));
        }
        repository.deleteById(id);
        return "Empresa deletada com sucesso";
    }

    public EmpresaResponse getEmpresaByEmailAndSenha(String email, String senha) {
        List<EmpresaEntity> empresa = repository.findByEmailAndSenha(email, senha);
        if (empresa.isEmpty()) {
            throw new EmpresaNaoExisteException("Email ou senha inválido");
        }
        return empresa.stream().map(empresaResponseMapper::from).toList().get(0);
    }

    public List<EmpresaResponse> getEmpresaBySetor(String setor) {
        List<EmpresaEntity> empresa = repository.findBySetor(setor);
        return empresa.stream().map(empresaResponseMapper::from).toList();
    }

    public List<EmpresaResponse> getEmpresaByNome(String nome) {
        List<EmpresaEntity> empresa = repository.findByRazaoSocial(nome);
        return empresa.stream().map(empresaResponseMapper::from).toList();
    }

    public List<EmpresaResponse> getEmpresaByCnpj(String cnpj) {
        List<EmpresaEntity> empresa = repository.findByCnpj(cnpj);
        return empresa.stream().map(empresaResponseMapper::from).toList();
    }

    public List<EmpresaResponse> getEmpresaByTelefone(String telefone) {
        List<EmpresaEntity> empresa = repository.findByTelefone(telefone);
        return empresa.stream().map(empresaResponseMapper::from).toList();
    }

    private AddressViaCep getAddressViaCep(String cep) {
        if (cep != null) {
            final AddressViaCep addressViaCep = viaCepApiClient.getAddress(cep);
            if (addressViaCep.cep() == null) {
                throw new EnderecoNaoEncontradoException("Endereco não com cep %s não existe".formatted(cep));
            }
            return addressViaCep;
        }
        return null;
    }
}

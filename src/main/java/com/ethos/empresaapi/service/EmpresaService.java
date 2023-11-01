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
            String message = "";
            if (e.getMessage().contains("cnpj")) {
                message = "Empresa com o CNPJ %s já cadastrada".formatted(entity.getCnpj());
            } else if (e.getMessage().contains("email")) {
                message = "Empresa com o email %s já cadastrada".formatted(entity.getEmail());
            } else if (e.getMessage().contains("telefone")) {
                message = "Empresa com o telefone %s já cadastrada".formatted(entity.getTelefone());
            } else if (e.getMessage().contains("razao_social")) {
                message = "Empresa com a razão social %s já cadastrada".formatted(entity.getRazaoSocial());
            }
            throw new EmpresaJaExisteException(message);
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
        if (!repository.existsById(id)) {
            throw new EmpresaNaoExisteException("Empresa com id %s não existe".formatted(id.toString()));
        }
        EmpresaEntity entity = repository.findById(id).get();

        if (request.razaoSocial() != null && !request.razaoSocial().isEmpty()) {
            entity.setRazaoSocial(request.razaoSocial());
        }
        if (request.telefone() != null && !request.telefone().isEmpty()) {
            entity.setTelefone(request.telefone());
        }
        if (request.cnpj() != null && !request.cnpj().isEmpty()) {
            entity.setCnpj(request.cnpj());
        }
        if (request.email() != null && !request.email().isEmpty()) {
            entity.setEmail(request.email());
        }
        if (request.senha() != null && !request.senha().isEmpty()) {
            entity.setSenha(request.senha());
        }
        if (request.setor() != null && !request.setor().isEmpty()) {
            entity.setSetor(request.setor());
        }
        if (request.qtdFuncionarios() != null) {
            entity.setQtdFuncionarios(request.qtdFuncionarios());
        }
        if (request.assinanteNewsletter() != null && request.assinanteNewsletter().describeConstable().isPresent()) {
            entity.setAssinanteNewsletter(request.assinanteNewsletter());
        }
        if (request.enderecoRequest() != null) {
            if (request.enderecoRequest().cep() != null && !request.enderecoRequest().cep().isEmpty()) {
                AddressViaCep addressViaCep = getAddressViaCep(request.enderecoRequest().cep());
                Empresa model = empresaModelMapper.from(request);
                Empresa modelAddressUpdated = model.updateEnderecoFrom(addressViaCep);
                entity.setEndereco(empresaEntityMapper.from(modelAddressUpdated).getEndereco());
            }
            if (request.enderecoRequest().numero() != null && !request.enderecoRequest().numero().isEmpty()) {
                entity.getEndereco().setNumero(request.enderecoRequest().numero());
            }
            if (request.enderecoRequest().complemento() != null && !request.enderecoRequest().complemento().isEmpty()) {
                entity.getEndereco().setComplemento(request.enderecoRequest().complemento());
            }
        }

        EmpresaEntity entityUpdated = saveEmpresa(entity);

        return empresaResponseMapper.from(entityUpdated);
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

    public List<EmpresaResponse> getEmpresaByAssinanteNewsletter(Boolean assinanteNewsletter) {
        List<EmpresaEntity> empresa = repository.findByAssinanteNewsletterOrderByAssinanteNewsletterAsc(assinanteNewsletter);
        return empresa.stream().map(empresaResponseMapper::from).toList();
    }
}

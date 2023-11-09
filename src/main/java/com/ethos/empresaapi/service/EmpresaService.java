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

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ethos.empresaapi.service.Arquivo.ListaObj;
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
        if(request.plano() != 1){
            repository.updatePlano(request.plano(), id);
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

    public ListaObj<EmpresaEntity> gerarListaObj() {
        List<EmpresaEntity> empresas = repository.findAll();
        ListaObj<EmpresaEntity> listaObj = new ListaObj<>(empresas.size());

        for (EmpresaEntity empresa : empresas) {
            listaObj.adiciona(empresa);
        }

        return listaObj;
    }
    public static void gravaRegistro(String registro, String nomeArq) {

        BufferedWriter saida = null;

        //Bloco try-catch para abrir o arquivo
        try{
            saida = new BufferedWriter(new FileWriter(nomeArq, true));
        }catch (IOException erro){
            System.out.println("Erro ao abrir o arquivo");
        }

        //Bloco try-catch para gravar o registro e fechar o arquivo

        try{
            saida.append(registro + "\n");
            saida.close();
        }catch(IOException erro){
            System.out.println("Erro ao gravar o arquivo");
            erro.printStackTrace();
        }

    }

    public static void gravaArquivoTxt(ListaObj<EmpresaEntity> lista, String nomeArq) {
        int contaRegistroDadosGravados = 0;
        StringBuilder content = new StringBuilder();

        // Registro de Header
        String header = "00EMPRESA";
        header = header + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        header = header + "01";
        content.append(header).append(System.lineSeparator());

        for (int i = 0; i < lista.getTamanho(); ++i) {
            EmpresaEntity e = (EmpresaEntity) lista.getElemento(i);

            // Registro de Corpo
            String corpo = "02";
            corpo = corpo + String.format("%-32s", e.getId());
            corpo = corpo + String.format("%-60s", e.getRazaoSocial());
            corpo = corpo + String.format("%-14s", e.getCnpj());
            corpo = corpo + String.format("%-11s", e.getTelefone());
            corpo = corpo + String.format("%-100s", e.getEmail());
            corpo = corpo + String.format("%010d", e.getQtdFuncionarios());
            corpo = corpo + String.format("%-45s", e.getSetor());
            corpo = corpo + (e.getAssinanteNewsletter() ? "True " : "False");
            corpo = corpo + String.format("%-32s", e.getEndereco());
//            corpo = corpo + String.format("%032d", e.getFkPlano());
            content.append(corpo).append(System.lineSeparator());
            ++contaRegistroDadosGravados;
        }

        // Registro de Trailer
        String trailer = "01";
        trailer = trailer + String.format("%05d", contaRegistroDadosGravados);
        content.append(trailer);

        // Grava o conteúdo no arquivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArq))) {
            writer.write(content.toString());
        } catch (IOException e) {
            System.err.println("Erro ao gravar no arquivo: " + e.getMessage());
        }
    }
    public static void lerArquivoTxt(String nomeArq) {
        BufferedReader entrada = null;
        int contaRegDadosLidos = 0;
        List<EmpresaEntity> listaLida = new ArrayList<>();

        try {
            entrada = new BufferedReader(new FileReader(nomeArq));
        } catch (IOException e) {
            System.err.println("Erro ao abrir o arquivo");
            e.printStackTrace();
            return;  // Saia da função em caso de erro de abertura do arquivo.
        }

        try {
            for (String registro = entrada.readLine(); registro != null; registro = entrada.readLine()) {
                String tipoRegistro = registro.substring(0, 2);
                if (tipoRegistro.equals("00")) {
                    System.out.println("É um registro de header");
                    System.out.println("Tipo de arquivo: " + registro.substring(2, 9));
                    System.out.println("Data/hora de geração do arquivo: " + registro.substring(9, 28));
                    System.out.println("Versão do layout: " + registro.substring(28, 30));
                } else if (tipoRegistro.equals("01")) {
                    System.out.println("É um registro de trailer");
                    int qtdRegDadosGravados = Integer.parseInt(registro.substring(2, 7));
                    if (qtdRegDadosGravados == contaRegDadosLidos) {
                        System.out.println("Quantidade de registros de dados é compatível com a quantidade de registros de dados lidos");
                    } else {
                        System.out.println("Quantidade de registros de dados é incompatível com a quantidade de registros de dados lidos");
                    }
                } else if (tipoRegistro.equals("02")) {
                    System.out.println("É um registro de corpo");
                    UUID id = UUID.fromString(registro.substring(2, 34).trim());
                    String razaoSocial = registro.substring(34, 94).trim();
                    String cnpj = registro.substring(94, 108);
                    String telefone = registro.substring(108, 119);
                    String email = registro.substring(119, 219);
                    int quantidadeFuncionarios = Integer.parseInt(registro.substring(219, 229));
                    String setor = registro.substring(229, 274).trim();
                    Boolean assinanteNewsletter = registro.substring(274, 279).trim().equals("True");
                    int endereco = Integer.parseInt(registro.substring(279, 311).trim());
                    int fkPlano = Integer.parseInt(registro.substring(311).trim());

                    EmpresaEntity empresa = new EmpresaEntity(id, razaoSocial, cnpj, telefone, email, quantidadeFuncionarios, setor, assinanteNewsletter, endereco, fkPlano);
                    listaLida.add(empresa);
                    ++contaRegDadosLidos;
                } else {
                    System.out.println("É um registro inválido");
                }
            }

            entrada.close();
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo");
            e.printStackTrace();
        }

        System.out.println("\nLista de Empresas Lida:");
        for (EmpresaEntity empresa : listaLida) {
            System.out.println(empresa);
        }
    }
}

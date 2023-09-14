package com.ethos.empresaapi.controller;

import com.ethos.empresaapi.controller.request.EmpresaRequest;
import com.ethos.empresaapi.controller.response.EmpresaResponse;
import com.ethos.empresaapi.service.EmpresaService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1.0/empresas")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EmpresaController {

    private final EmpresaService empresaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmpresaResponse postEmpresa(@RequestBody @Valid EmpresaRequest request) {
        return empresaService.postEmpresa(request);
    }

    @GetMapping("/teste")
    public String teste() {
        return "Teste";
    }

    @GetMapping
    public List<EmpresaResponse> getAllEmpresa(@RequestParam(value = "nome", required = false) String nome,
                                               @RequestParam(value = "cnpj", required = false) String cnpj,
                                               @RequestParam(value = "telefone", required = false) String telefone,
                                               @RequestParam(value = "setor", required = false) String setor) {
        if (nome != null) {
            return empresaService.getEmpresaByNome(nome);
        } else if (cnpj != null) {
            return empresaService.getEmpresaByCnpj(cnpj);
        } else if (telefone != null) {
            return empresaService.getEmpresaByTelefone(telefone);
        } else if (setor != null) {
            return empresaService.getEmpresaBySetor(setor);
        }
        return empresaService.getAllEmpresa();
    }

    @GetMapping("/{id}")
    public EmpresaResponse getEmpresaById(@PathVariable UUID id) {
        return empresaService.getEmpresaById(id);
    }

    @PutMapping("/{id}")
    public EmpresaResponse putEmpresaById(@PathVariable UUID id, @RequestBody EmpresaRequest request) {
        return empresaService.putEmpresaById(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteEmpresaById(@PathVariable UUID id) {
        return empresaService.deleteEmpresaById(id);
    }

    @GetMapping("/login/{email}/{senha}")
    public EmpresaResponse getEmpresaByEmailAndSenha(@PathVariable String email, @PathVariable String senha) {
        return empresaService.getEmpresaByEmailAndSenha(email, senha);
    }
}

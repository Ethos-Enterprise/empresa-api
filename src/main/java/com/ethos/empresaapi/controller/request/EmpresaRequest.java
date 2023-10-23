package com.ethos.empresaapi.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.br.CNPJ;

public record EmpresaRequest(@NotBlank(message = "razão social em branco") @NotNull(message = "razão social nula") String razaoSocial,
                             @NotBlank(message = "cnpj em branco") @NotNull(message = "cnpj nulo") @CNPJ(message = "Cnpj inválido") String cnpj,
                             @NotBlank(message = "telefone em branco") @NotNull(message = "telefone nulo") String telefone,
                             @NotNull(message = "email nulo") @NotBlank(message = "email em branco") @Email(message = "email inválido") String email,
                             @NotNull(message = "senha nula") @NotBlank(message = "senha em branco") String senha, String setor,
                             Integer qtdFuncionarios,
                             EnderecoRequest enderecoRequest,
                             Boolean assinanteNewsletter) {
    public record EnderecoRequest(String cep, String numero, String complemento) {
        @Builder(toBuilder = true)
        public EnderecoRequest {
        }
    }
}

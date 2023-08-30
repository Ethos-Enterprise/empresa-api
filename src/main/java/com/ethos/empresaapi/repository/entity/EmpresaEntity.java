package com.ethos.empresaapi.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

@Getter
@Table(name = "EMPRESA")
@Entity
@Immutable
public class EmpresaEntity {
    @Id
    UUID id;

    String nome;

    String cnpj;

    String telefone;

    String email;

    String senha;

    String setor;

    Integer quantidadeFuncionarios;

    public EmpresaEntity() {
    }
    @Builder(toBuilder = true)
    public EmpresaEntity(String nome, String cnpj, String telefone, String email, String senha, String setor, Integer quantidadeFuncionarios) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.setor = setor;
        this.quantidadeFuncionarios = quantidadeFuncionarios;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public void setQuantidadeFuncionarios(Integer quantidadeFuncionarios) {
        this.quantidadeFuncionarios = quantidadeFuncionarios;
    }
}

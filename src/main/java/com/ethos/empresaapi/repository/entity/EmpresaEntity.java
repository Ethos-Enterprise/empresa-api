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

    String razaoSocial;

    String cnpj;

    String telefone;

    String email;

    String senha;

    String setor;

    Integer qtdFuncionarios;

    public EmpresaEntity() {
    }
    @Builder(toBuilder = true)
    public EmpresaEntity(String razaoSocial, String cnpj, String telefone, String email, String senha, String setor, Integer qtdFuncionarios) {
        this.id = UUID.randomUUID();
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.setor = setor;
        this.qtdFuncionarios = qtdFuncionarios;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
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

    public void setQtdFuncionarios(Integer qtdFuncionarios) {
        this.qtdFuncionarios = qtdFuncionarios;
    }
}

package com.ethos.empresaapi.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Builder;
import org.hibernate.annotations.Immutable;

@Table(name = "EMPRESA")
@Entity
@Immutable
public class EmpresaEntity {
    @Id
    UUID id;

    String nome;

    String cnpj;

    String telefone;

    public EmpresaEntity() {
    }
    @Builder(toBuilder = true)
    public EmpresaEntity(String nome, String cnpj, String telefone) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.cnpj = cnpj;
        this.telefone = telefone;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}

package com.ethos.empresaapi.repository.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Builder;

@Table(name = "EMPRESA")
@Entity
public class EmpresaEntity {
    @Id
    UUID id;

    String razaoSocial;

    String cnpj;

    String telefone;

    String email;

    String senha;

    String setor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_endereco", referencedColumnName = "id")
    EnderecoEntity endereco;

    Integer qtdFuncionarios;

    Boolean assinanteNewsletter;
    int plano;

    public EmpresaEntity() {
    }

    @Builder(toBuilder = true)
    public EmpresaEntity(String razaoSocial, String cnpj, String telefone, String email, String senha, String setor, Integer qtdFuncionarios,
                         Boolean assinanteNewsletter, EnderecoEntity endereco, int plano) {
        this.id = UUID.randomUUID();
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.setor = setor;
        this.qtdFuncionarios = qtdFuncionarios;
        this.assinanteNewsletter = assinanteNewsletter;
        this.endereco = endereco;
        this.plano = plano;
    }


    public EmpresaEntity(UUID id, String razaoSocial, String cnpj, String telefone, String email, int quantidadeFuncionarios, String setor,
                         Boolean assinanteNewsletter, int endereco, int plano) {
    }

    public void setAssinanteNewsletter(Boolean assinanteNewsletter) {
        this.assinanteNewsletter = assinanteNewsletter;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public EnderecoEntity getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoEntity endereco) {
        this.endereco = endereco;
    }

    public Integer getQtdFuncionarios() {
        return qtdFuncionarios;
    }

    public void setQtdFuncionarios(Integer qtdFuncionarios) {
        this.qtdFuncionarios = qtdFuncionarios;
    }

    public void setPlano(int plano) {
        this.plano = plano;
    }
}

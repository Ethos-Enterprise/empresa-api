package com.ethos.empresaapi.repository.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Immutable;

import java.util.UUID;

@Getter
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_plano", referencedColumnName = "id")
    PlanoEntity plano;

    public EmpresaEntity() {
    }

    @Builder(toBuilder = true)
    public EmpresaEntity(String razaoSocial, String cnpj, String telefone, String email, String senha, String setor,
                         Integer qtdFuncionarios, Boolean assinanteNewsletter, EnderecoEntity endereco, PlanoEntity plano) {
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


    public EmpresaEntity(UUID id, String razaoSocial, String cnpj, String telefone, String email, int quantidadeFuncionarios, String setor, Boolean assinanteNewsletter, int endereco, int plano) {
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
    public void setAssinanteNewsletter(Boolean assinanteNewsletter) {
        this.assinanteNewsletter = assinanteNewsletter;
    }

    public void setEndereco(EnderecoEntity endereco) {
        this.endereco = endereco;
    }

    public Boolean getAssinanteNewsletter() {
        return assinanteNewsletter;
    }

    public void setPlano(PlanoEntity plano) {
        this.plano = plano;
    }
}

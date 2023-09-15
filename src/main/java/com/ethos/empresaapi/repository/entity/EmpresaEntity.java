package com.ethos.empresaapi.repository.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Formula;
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

    @Formula("(select * from consultar_empresa_prestadora_por_id(id))")
    String status_prestadora;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    EnderecoEntity endereco;

    Integer qtdFuncionarios;

    public EmpresaEntity() {
    }

    @Builder(toBuilder = true)
    public EmpresaEntity(String razaoSocial, String cnpj, String telefone, String email, String senha, String setor, EnderecoEntity endereco,
                         Integer qtdFuncionarios, String status_prestadora) {
        this.id = UUID.randomUUID();
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.setor = setor;
        this.endereco = endereco;
        this.qtdFuncionarios = qtdFuncionarios;
        this.status_prestadora = status_prestadora;
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
    public void setStatus_prestadora(String status_prestadora) {
        this.status_prestadora = status_prestadora;
    }

    public void setEndereco(EnderecoEntity endereco) {
        this.endereco = endereco;
    }

}

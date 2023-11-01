package com.ethos.empresaapi.repository.entity;

import jakarta.persistence.*;

import java.util.UUID;
import lombok.Builder;
import org.hibernate.annotations.Immutable;
@Table(name = "Endereco")
@Entity
@Immutable
public class EnderecoEntity {
    @Id
    UUID id;

    String cep;

    String numero;

    String logradouro;

    String complemento;

    String bairro;

    String uf;
    public EnderecoEntity() {
    }
    @Builder(toBuilder = true)
    public EnderecoEntity(String cep, String numero, String logradouro, String complemento, String bairro, String uf) {
        this.id = UUID.randomUUID();
        this.numero = numero;
        this.cep = cep;
        this.logradouro = logradouro;
        this.complemento = complemento;
        this.bairro = bairro;
        this.uf = uf;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public UUID getId() {
        return id;
    }

    public String getCep() {
        return cep;
    }

    public String getNumero() {

         return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public String getUf() {
        return uf;
    }

}

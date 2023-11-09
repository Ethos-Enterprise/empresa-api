package com.ethos.empresaapi.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import org.hibernate.annotations.Immutable;

@Table(name = "Plano")
@Entity
@Immutable
public class PlanoEntity {

    @Id
    int id;

    String tipo;
    Double valor;

    public PlanoEntity() {
    }

    @Builder(toBuilder = true)
    public PlanoEntity(int id, String tipo, Double valor) {
        this.id = id;
        this.tipo = tipo;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}

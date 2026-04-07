package com.igrejacristahangar.cantina.infra.controller.order.enums;

public enum STATUS {
    PENDENTE("Pendente"),
    PREPARANDO("Preparando"),
    CONCLUIDO("Concluído");

    private final String descricao;

    STATUS(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

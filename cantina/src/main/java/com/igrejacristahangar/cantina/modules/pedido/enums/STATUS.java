package com.igrejacristahangar.cantina.modules.pedido.enums;

public enum STATUS {
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

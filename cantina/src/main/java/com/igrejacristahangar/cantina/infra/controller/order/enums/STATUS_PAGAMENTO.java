package com.igrejacristahangar.cantina.infra.controller.order.enums;

public enum STATUS_PAGAMENTO {
    PENDENTE("Pendente"),
    EFETUADO("Efetuado");

    private final String descricao;

    STATUS_PAGAMENTO(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

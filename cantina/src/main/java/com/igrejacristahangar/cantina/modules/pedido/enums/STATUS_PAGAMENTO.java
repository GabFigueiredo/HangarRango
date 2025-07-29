package com.igrejacristahangar.cantina.modules.pedido.enums;

public enum STATUS_PAGAMENTO {
    PENDENTE("Pendente"),
    EFETUADO("Efetuado"),
    MARCADO("Marcado");

    private final String descricao;

    STATUS_PAGAMENTO(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

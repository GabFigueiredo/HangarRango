package com.igrejacristahangar.cantina.infra.controller.order.enums;

public enum FORMA_PAGAMENTO {
    PIX("Pix"),
    CARTAO("Cartão"),
    DINHEIRO("Dinheiro"),
    MARCADO("Marcado");

    private final String descricao;

    FORMA_PAGAMENTO(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

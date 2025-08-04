package com.igrejacristahangar.cantina.modules.pedido.enums;

public enum FORMA_PAGAMENTO {
    PIX("Pix"),
    CARTAO("Cartão"),
    MARCADO("Marcado");

    private final String descricao;

    FORMA_PAGAMENTO(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

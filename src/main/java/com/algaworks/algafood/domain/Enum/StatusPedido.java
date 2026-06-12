package com.algaworks.algafood.domain.Enum;

public enum StatusPedido {
    CRIADO("Criado"),
    CONFIRMADO("Confirmado"),
    ENTREGUE("Entregue"),
    CANCELADO("Cancelado");

    private String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public boolean podeAlterarPara(StatusPedido novoStatus) {
        return switch (this) {
            case CRIADO -> novoStatus == CONFIRMADO
                    || novoStatus == CANCELADO;

            case CONFIRMADO -> novoStatus == ENTREGUE
                    || novoStatus == CANCELADO;

            case ENTREGUE, CANCELADO -> false;
        };
    }
}

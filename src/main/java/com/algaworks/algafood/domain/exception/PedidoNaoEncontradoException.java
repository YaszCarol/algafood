package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final Long serialVersionUID = 1L;
    public PedidoNaoEncontradoException(String message) {
        super(message);
    }

    public PedidoNaoEncontradoException(Long pedidoId) {

        this(String.format("Não existe pedido com codigo %s", pedidoId));
    }
}

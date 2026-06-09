package com.algaworks.algafood.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public FormaPagamentoNaoEncontradaException(String message) {
        super(message);
    }

    public FormaPagamentoNaoEncontradaException(Long formapagamentoId) {
        this(String.format("Forma de pagamento com id %d nao encontrado", formapagamentoId));
    }

}

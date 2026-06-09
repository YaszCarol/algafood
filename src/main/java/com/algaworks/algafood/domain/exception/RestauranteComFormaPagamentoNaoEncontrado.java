package com.algaworks.algafood.domain.exception;

public class RestauranteComFormaPagamentoNaoEncontrado extends EntidadeNaoEncontradaException {
    public RestauranteComFormaPagamentoNaoEncontrado(String formapagamento) {
        super(String.format("Forma de pagamento '%s' nao é aceita por esse restaurante", formapagamento));
    }
}

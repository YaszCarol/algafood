package com.algaworks.algafood.domain.exception;

public class CidadeNaoEncontradaException extends RuntimeException {

   private static final long serialVersionUID = 1L;

    public CidadeNaoEncontradaException(String message) {
        super(message);
    }

    public CidadeNaoEncontradaException(Long cidadeId) {
        this(String.format("Cidade com id %d nao encontrada", cidadeId));
    }
}

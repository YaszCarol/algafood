package com.algaworks.algafood.domain.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final Long serialVersionUID =1L;

    public PermissaoNaoEncontradaException(String message) {
        super(message);
    }

    public PermissaoNaoEncontradaException(Long permissaoId) {
        this(String.format("Permissao com Id %s nao encontrado", permissaoId));
    }
}

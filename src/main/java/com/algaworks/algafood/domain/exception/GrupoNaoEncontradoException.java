package com.algaworks.algafood.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException{

    private static final Long serialVersionUID = 1L;

    public GrupoNaoEncontradoException(String message) {
        super(message);
    }

    public GrupoNaoEncontradoException(Long grupoId) {
        this(String.format("Grupo com id %d nao encontrado", grupoId));
    }
}

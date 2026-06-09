package com.algaworks.algafood.domain.exception;

public class UsuarioNaoEncontrado extends EntidadeNaoEncontradaException{

    private static final Long serialVersionUID = 1L;

    public UsuarioNaoEncontrado(String message) {
        super(message);
    }

    public UsuarioNaoEncontrado(Long usuarioId) {
        this(String.format("Usuario com id %d nao encontrado", usuarioId));
    }

}

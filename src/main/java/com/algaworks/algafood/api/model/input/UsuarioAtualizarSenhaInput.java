package com.algaworks.algafood.api.model.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioAtualizarSenhaInput {

    @NotBlank
    @NotNull
    private String senhaAtual;

    @NotBlank
    private String novaSenha;
}

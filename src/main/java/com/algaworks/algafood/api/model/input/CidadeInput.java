package com.algaworks.algafood.api.model.input;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInput {

    private String nome;

    @Valid
    private EstadoIdInput estado;
}

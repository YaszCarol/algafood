package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class CozinhaModel extends RepresentationModel<CozinhaModel> {

    private Long id;
    private String nome;
}

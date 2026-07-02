package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class PermissaoModel extends RepresentationModel<PermissaoModel> {

    private Long id;

    private String descricao;

    private String nome;
}

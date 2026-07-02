package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoModel extends RepresentationModel<ProdutoModel> {

    private Long id;

    private  String nome;

    private String descricao;

    private Boolean ativo;

    private BigDecimal preco;
}

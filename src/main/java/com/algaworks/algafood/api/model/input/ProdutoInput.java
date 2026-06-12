package com.algaworks.algafood.api.model.input;

import com.algaworks.algafood.domain.model.Restaurante;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoInput {

    @NotBlank
    private  String nome;

    @NotBlank
    private String descricao;

    @NotNull
    private Boolean ativo;

    @NotNull
    @PositiveOrZero
    private BigDecimal preco;

}

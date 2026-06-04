package com.algaworks.algafood.api.model.input;

import com.algaworks.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CozinhaInput {

    @NotBlank
    private String nome;

    @JsonIgnore
    private List<RestauranteInput> restaurantes = new ArrayList<>();
}

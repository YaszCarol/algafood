package com.algaworks.algafood.api.model;

import com.algaworks.algafood.domain.Enum.StatusPedido;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class PedidoResumoModel {

    private String codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private OffsetDateTime dataCriacao;
    private StatusPedido status;
    private RestauranteResumoModel restaurante;
    private UsuarioModel cliente;
}

package com.algaworks.algafood.api.model;

import com.algaworks.algafood.domain.Enum.StatusPedido;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class PedidoResumoModel extends RepresentationModel<PedidoResumoModel> {

    private String codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private OffsetDateTime dataCriacao;
    private StatusPedido status;
    private RestauranteApenasNomeModel restaurante;
    //private String restauranteId;
    private UsuarioModel cliente;
    //private String nomeCliente;
}

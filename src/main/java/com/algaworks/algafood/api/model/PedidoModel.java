package com.algaworks.algafood.api.model;

import com.algaworks.algafood.domain.Enum.StatusPedido;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoModel extends RepresentationModel<PedidoModel>{

    private RestauranteApenasNomeModel restaurante;

    private String codigo;

    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;

    private OffsetDateTime dataCriacao;

    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataCancelamento;
    private OffsetDateTime dataEntrega;

    private StatusPedido status;

    private UsuarioModel cliente;

    private FormaPagamentoModel formaPagamento;

    private EnderecoModel enderecoEntrega;

    private List<ItemPedidoModel> itens;

}

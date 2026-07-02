package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.*;
import com.algaworks.algafood.api.model.*;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public PedidoModelAssembler() {
        super(PedidoController.class, PedidoModel.class);
    }

    @Override
    public PedidoModel toModel(Pedido pedido) {
        PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);

        modelMapper.map(pedido, pedidoModel);

        CidadeResumoModel cidade = pedidoModel.getEnderecoEntrega().getCidade();
        List<ItemPedidoModel> itensPedido = pedidoModel.getItens();
        RestauranteApenasNomeModel restaurante = pedidoModel.getRestaurante();
        UsuarioModel usuario = pedidoModel.getCliente();
        FormaPagamentoModel formaPagamento = pedidoModel.getFormaPagamento();

        pedidoModel.add(algaLinks.linkToPedidos("pedidos"));

        if (pedido.podeSerConfirmado())
            pedidoModel.add(algaLinks.linkToConfirmarPedido(pedidoModel.getCodigo(),"confirmacao" ));

        if (pedido.podeSerCancelado())
            pedidoModel.add(algaLinks.linkToCancelarPedido(pedidoModel.getCodigo(), "cancelar"));

        if (pedido.podeSerEntregue())
            pedidoModel.add(algaLinks.linkToEntregarPedido(pedidoModel.getCodigo(), "entregar"));

        usuario.add(algaLinks.linkToUsuarios(usuario.getId()));

        restaurante.add(algaLinks.linkToRestaurante(restaurante.getId()));

        formaPagamento.add(algaLinks.linkToFormasPagamento(formaPagamento.getId()));

        cidade.add(algaLinks.linkToCidade(cidade.getId()));

        itensPedido.forEach(itemPedidoModel -> itemPedidoModel.
                add(algaLinks.linkToProduto(restaurante.getId(), itemPedidoModel.getProdutoId(), "produto")));

        return pedidoModel;
    }
}

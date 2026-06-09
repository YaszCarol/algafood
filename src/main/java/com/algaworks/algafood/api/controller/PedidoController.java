package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteComFormaPagamentoNaoEncontrado;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.FormaPagamentoService;
import com.algaworks.algafood.domain.service.PedidoService;
import com.algaworks.algafood.domain.service.RestauranteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final RestauranteService restauranteService;
    private final FormaPagamentoService formaPagamentoService;
    private final PedidoAssembler pedidoAssembler;
    private final PedidoResumoModelAssembler pedidoResumoModelAssembler;

    @GetMapping
    public List<PedidoResumoModel> listar() {
        List<Pedido> pedidos = pedidoService.listar();

        return pedidoResumoModelAssembler.toCollectionModel(pedidos);
    }

    @GetMapping("/{pedidoId}")
    public PedidoModel buscar(@PathVariable Long pedidoId) {
        Pedido pedido = pedidoService.buscar(pedidoId);

        return pedidoAssembler.toModel(pedido);
    }

    @PostMapping
    public void adicionar(@RequestBody @Valid PedidoInput pedidoInput) {

        try {
            restauranteService.containsFormaPagamento(pedidoInput.getRestaurante().getId(), pedidoInput.getFormaPagamento().getId());

        } catch (RestauranteComFormaPagamentoNaoEncontrado | FormaPagamentoNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }

    }
}

package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.*;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final RestauranteService restauranteService;
    private final FormaPagamentoService formaPagamentoService;
    private final CidadeService cidadeService;
    private final ProdutoService produtoService;
    private final UsuarioService usuarioService;

    public List<Pedido> listar(Specification<Pedido> pedidoSpecification) {
        return pedidoRepository.findAll(pedidoSpecification);
    }

    public Page<Pedido> listar(Specification<Pedido> pedidoSpecification, Pageable pageable) {
        return pedidoRepository.findAll(pedidoSpecification, pageable);
    }

    public Pedido buscar(String codigoId) {
        return pedidoRepository.findByCodigo(codigoId).orElseThrow(() -> new PedidoNaoEncontradoException(codigoId));
    }

    public void salvar(Pedido pedido) {
        pedidoRepository.save(pedido);
    }

    public Pedido emitir(Pedido pedido) {
        validarPedido(pedido);
        validarItens(pedido);

        BigDecimal taxaFrete = pedido.getRestaurante().getTaxaFrete();

        pedido.setTaxaFrete(taxaFrete);
        pedido.calcularValorTotal();

        this.salvar(pedido);

        return pedido;
    }

    public void validarPedido(Pedido pedido) {
        FormaPagamento formaPagamento = formaPagamentoService.buscar(pedido.getFormaPagamento().getId());
        Restaurante restaurante = restauranteService.buscar(pedido.getRestaurante().getId());
        Cidade cidade = cidadeService.buscar(pedido.getEnderecoEntrega().getCidade().getId());
        Usuario usuario = usuarioService.buscar(1L);

        if (!restauranteService.containsFormaPagamento(restaurante.getId(), formaPagamento.getId())) {
            throw new NegocioException(String.format(String.format("Forma de pagamento '%s' nao é aceita por esse restaurante", formaPagamento.getDescricao())));
        }

        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);
        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setCliente(usuario);
    }

    public void validarItens(Pedido pedido) {

        List<ItemPedido> itensPedido = pedido.getItens();
        Long restauranteId = pedido.getRestaurante().getId();

        for (ItemPedido item : itensPedido) {
            Produto produto = produtoService.buscar(item.getProduto().getId(), restauranteId);

            item.setPedido(pedido);
            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());
            item.calcularPrecoTotal();
        }
    }

    @Transactional
    public void confirmar(String codigoId) {
        Pedido pedido = this.buscar(codigoId);
        pedido.confirmar();
    }

    @Transactional
    public void cancelar(String codigoId) {
        Pedido pedido = this.buscar(codigoId);

        pedido.cancelar();
    }

    @Transactional
    public void entregar(String codigoId) {
        Pedido pedido = this.buscar(codigoId);

        pedido.entregar();
    }
}

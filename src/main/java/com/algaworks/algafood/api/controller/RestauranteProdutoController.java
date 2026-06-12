package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.ProdutoAssembler;
import com.algaworks.algafood.api.disassembler.ProdutoDisassembler;
import com.algaworks.algafood.api.model.ProdutoModel;
import com.algaworks.algafood.api.model.input.ProdutoInput;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.ProdutoService;
import com.algaworks.algafood.domain.service.RestauranteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/restaurantes/{restauranteId}/produtos")
@RestController
public class RestauranteProdutoController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private ProdutoAssembler produtoAssembler;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoDisassembler produtoDisassembler;

    @GetMapping
    public List<ProdutoModel> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);

        List<Produto> produtos = restaurante.getProdutos();

        return produtoAssembler.toCollectionModel(produtos);
    }

    @GetMapping("/{produtoId}")
    public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {

        Produto produto = produtoService.buscar(produtoId, restauranteId);

        return produtoAssembler.toModel(produto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {

        Restaurante restaurante = restauranteService.buscar(restauranteId);

        Produto produto = produtoDisassembler.toDomainObject(produtoInput);

        produto.setRestaurante(restaurante);

        produtoService.salvar(produto);

        return produtoAssembler.toModel(produto);
    }

    @PutMapping("/{produtoId}")
    public ProdutoModel atualizar(@PathVariable Long restauranteId,
                                  @PathVariable Long produtoId,
                                  @RequestBody @Valid ProdutoInput produtoInput) {

        Produto produto = produtoService.buscar(produtoId, restauranteId);

        produtoDisassembler.copyToObject(produtoInput, produto);

        produtoService.salvar(produto);

        return produtoAssembler.toModel(produto);

    }
}

package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FormaPagamentoAssembler;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
@RestController
public class RestauranteFormaPagamentoController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private FormaPagamentoAssembler formaPagamentoAssembler;

    @GetMapping
    public List<FormaPagamentoModel> listar(@PathVariable Long restauranteId) {

        Restaurante restaurante = restauranteService.buscar(restauranteId);

        return formaPagamentoAssembler.toCollectionModel(restaurante.getFormasPagamento());
    }

    @DeleteMapping("/{formapagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long formapagamentoId) {
        restauranteService.desassociarFormaPagamento(restauranteId, formapagamentoId);
    }

    @PutMapping("/{formapagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, @PathVariable Long formapagamentoId) {
        restauranteService.associarFormaPagamento(restauranteId, formapagamentoId);
    }

}

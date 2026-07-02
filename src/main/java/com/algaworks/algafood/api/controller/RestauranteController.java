package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.RestauranteApenasNomeModelAssembler;
import com.algaworks.algafood.api.assembler.RestauranteBasicoModelAssembler;
import com.algaworks.algafood.api.assembler.RestauranteModelAssembler;
import com.algaworks.algafood.api.disassembler.RestaurateInputDisassembler;
import com.algaworks.algafood.api.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.api.model.input.view.RestauranteView;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/restaurantes")
@RestController
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private RestauranteModelAssembler restauranteAssembler;

    @Autowired
    private RestauranteBasicoModelAssembler restauranteBasicoModelAssembler;

    @Autowired
    private RestauranteApenasNomeModelAssembler restauranteApenasNomeModelAssembler;

    @Autowired
    private RestaurateInputDisassembler restauranteDisassembler;

    @GetMapping
    public CollectionModel<RestauranteBasicoModel> listar() {
        List<Restaurante> restaurantes = restauranteService.listar();

        return restauranteBasicoModelAssembler.toCollectionModel(restaurantes);
    }

//    @JsonView(RestauranteView.Resumo.class)
//    @GetMapping(params = "projecao=resumo")
//    public CollectionModel<Restau> listarResumo() {
//        List<Restaurante> restaurantes = restauranteService.listar();
//
//        return restauranteApenasNomeModelAssembler.toCollectionModel(restaurantes);
//    }

  //  @JsonView(RestauranteView.ResumoNome.class)
    @GetMapping(params = "projecao=apenas-nome")
    public CollectionModel <RestauranteApenasNomeModel> listarApenasNome() {
        List<Restaurante> restaurantes = restauranteService.listar();

        return restauranteApenasNomeModelAssembler.toCollectionModel(restaurantes);
    }

    @GetMapping("/{restauranteId}")
    public RestauranteModel buscar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);

        return restauranteAssembler.toModel(restaurante);
    }

    @PostMapping
    public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {

        try {

            Restaurante restaurante = restauranteDisassembler.toDomainObject(restauranteInput);

            Restaurante restauranteAdd = restauranteService.salvar(restaurante);

            return restauranteAssembler.toModel(restauranteAdd);

        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException exception) {
            throw new NegocioException(exception.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public RestauranteModel atualizar(@RequestBody @Valid RestauranteInput restauranteInput, @PathVariable Long restauranteId) {

        Restaurante restauranteAtual = restauranteService.buscar(restauranteId);

        restauranteDisassembler.copyToObject(restauranteInput, restauranteAtual);

        try {
            restauranteService.salvar(restauranteAtual);
            return restauranteAssembler.toModel(restauranteAtual);
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException exception) {
            throw new NegocioException(exception.getMessage());
        }
    }

    @DeleteMapping("/{restauranteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long restauranteId) {
        restauranteService.remover(restauranteId);
    }


    @PutMapping("/{restauranteId}/ativo")
    public ResponseEntity<Void> ativar(@PathVariable Long restauranteId){

        restauranteService.ativar(restauranteId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{restauranteId}/ativo")
    public ResponseEntity<Void> inativar(@PathVariable Long restauranteId){

        restauranteService.inativar(restauranteId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{restauranteId}/fechamento")
    public ResponseEntity<Void> fechar(@PathVariable Long restauranteId){

        restauranteService.fechar(restauranteId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{restauranteId}/aberto")
    public ResponseEntity<Void> abrir(@PathVariable Long restauranteId){

        restauranteService.abrir(restauranteId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> restauranteIds) {
        try {
            restauranteService.ativar(restauranteIds);
        } catch (RestauranteNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarMultiplos(@RequestBody List<Long> restauranteIds) {
        try {
            restauranteService.inativar(restauranteIds);
        } catch (RestauranteNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }
}


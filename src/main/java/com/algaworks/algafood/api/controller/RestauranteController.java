package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.RestauranteModelAssembler;
import com.algaworks.algafood.api.disassembler.RestaurateInputDisassembler;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.exc.PropertyBindingException;
import tools.jackson.databind.json.JsonMapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("/restaurantes")
@RestController
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private RestauranteModelAssembler restauranteAssembler;

    @Autowired
    private RestaurateInputDisassembler restauranteDisassembler;

    @GetMapping
    public List<RestauranteModel> listar() {
        List<Restaurante> restaurantes = restauranteService.listar();

        return restauranteAssembler.toCollectionModel(restaurantes);
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long restauranteId){

        restauranteService.ativar(restauranteId);
    }

    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long restauranteId){

        restauranteService.inativar(restauranteId);
    }

    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechar(@PathVariable Long restauranteId){

        restauranteService.fechar(restauranteId);
    }

    @PutMapping("/{restauranteId}/aberto")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrir(@PathVariable Long restauranteId){

        restauranteService.abrir(restauranteId);
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


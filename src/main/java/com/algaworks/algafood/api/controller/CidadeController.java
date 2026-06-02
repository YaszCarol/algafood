package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/cidades")
@RestController
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    @GetMapping
    public List<Cidade> listar() {
        return cidadeService.listar();
    }

    @GetMapping("/{cidadeId}")
    public Cidade buscar(@PathVariable Long cidadeId) {
        return cidadeService.buscar(cidadeId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade adicionar(@RequestBody @Valid Cidade cidade) {
        try {
            return cidadeService.salvar(cidade);
        } catch (EstadoNaoEncontradoException exception) {
            throw new NegocioException(exception.getMessage(), exception);
        }
    }

    @PutMapping("/{cidadeId}")
    public Cidade atualizar(@PathVariable Long cidadeId, @RequestBody @Valid Cidade cidade) {

        Cidade cidadeEncontrada = cidadeService.buscar(cidadeId);

        BeanUtils.copyProperties(cidade, cidadeEncontrada, "id");

        try {
            return cidadeService.salvar(cidadeEncontrada);
        } catch (EstadoNaoEncontradoException exception) {
            throw new NegocioException(exception.getMessage(), exception);
        }
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        cidadeService.remover(cidadeId);
    }
}

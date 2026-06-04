package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CidadeAssembler;
import com.algaworks.algafood.api.disassembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;
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

    @Autowired
    private CidadeAssembler cidadeAssembler;

    @Autowired
    private CidadeInputDisassembler cidadeDisassembler;

    @GetMapping
    public List<CidadeModel> listar() {

        List<Cidade> cidades = cidadeService.listar();

        return cidadeAssembler.toCollectionModel(cidades);
    }

    @GetMapping("/{cidadeId}")
    public CidadeModel buscar(@PathVariable Long cidadeId) {

        Cidade cidade = cidadeService.buscar(cidadeId);

        return cidadeAssembler.toModel(cidade);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeDisassembler.toDomainObject(cidadeInput);

            Cidade cidadeSalva = cidadeService.salvar(cidade);

            return cidadeAssembler.toModel(cidadeSalva);
        } catch (EstadoNaoEncontradoException exception) {
            throw new NegocioException(exception.getMessage(), exception);
        }
    }

    @PutMapping("/{cidadeId}")
    public CidadeModel atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {

        Cidade cidadeEncontrada = cidadeService.buscar(cidadeId);

        cidadeDisassembler.copyToObject(cidadeInput, cidadeEncontrada);

        try {
            Cidade cidade = cidadeService.salvar(cidadeEncontrada);

            return cidadeAssembler.toModel(cidade);
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

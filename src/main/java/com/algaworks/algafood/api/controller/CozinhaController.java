package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CozinhaModelAssembler;
import com.algaworks.algafood.api.disassembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.input.CozinhaInput;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RequestMapping("/cozinhas")
@RestController
public class CozinhaController {

    @Autowired
    private CadastroCozinhaService cozinhaService;

    @Autowired
    private CozinhaModelAssembler cozinhaAssembler;

    @Autowired
    private CozinhaInputDisassembler cozinhaDisassembler;


    @GetMapping
    public Page<CozinhaModel> listar(Pageable pageable) {
        Page<Cozinha> cozinhasPage = cozinhaService.listar(pageable);

        // para cada Cozinha de cozinhasPage.getContent aplica a funcao de converter para model
        return cozinhasPage.map(cozinhaAssembler::toModel);
    }

    @GetMapping("/{cozinhaId}")
    public CozinhaModel buscar(@PathVariable Long cozinhaId) {
        Cozinha cozinha = cozinhaService.buscar(cozinhaId);

        return cozinhaAssembler.toModel(cozinha);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {

        Cozinha cozinhaDomain = cozinhaDisassembler.toDomainObject(cozinhaInput);

        Cozinha cozinha = cozinhaService.salvar(cozinhaDomain);

        return cozinhaAssembler.toModel(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public Cozinha atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInput cozinhaInput) {

        Cozinha cozinhaEncontrada = cozinhaService.buscar(cozinhaId);

        cozinhaDisassembler.copyToObject(cozinhaInput, cozinhaEncontrada);

        return cozinhaService.salvar(cozinhaEncontrada);
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long cozinhaId) {
        cozinhaService.remover(cozinhaId);
    }

}

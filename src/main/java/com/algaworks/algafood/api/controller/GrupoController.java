package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.GrupoAssembler;
import com.algaworks.algafood.api.disassembler.GrupoDisassembler;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.GrupoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private GrupoAssembler grupoAssembler;

    @Autowired
    private GrupoDisassembler grupoDisassembler;

    @GetMapping
    public List<GrupoModel> listar() {

        List<Grupo> grupos = grupoService.listar();
        return grupoAssembler.toCollectionModel(grupos);
    }

    @GetMapping("/{grupoId}")
    public GrupoModel buscar(@PathVariable Long grupoId) {

        Grupo grupo = grupoService.buscar(grupoId);

        return grupoAssembler.toModel(grupo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoModel adicionar(@RequestBody @Valid GrupoInput grupoInput) {

        Grupo grupo = grupoDisassembler.toDomainModel(grupoInput);

        grupoService.salvar(grupo);

        return grupoAssembler.toModel(grupo);

    }

    @PutMapping("/{grupoId}")
    public GrupoModel atualizar(@PathVariable Long grupoId, @RequestBody @Valid GrupoInput grupoInput) {

        Grupo grupo = grupoService.buscar(grupoId);

        grupoDisassembler.copyDomainObject(grupoInput, grupo);

        grupoService.salvar(grupo);

        return grupoAssembler.toModel(grupo);
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long grupoId) {
        grupoService.deletar(grupoId);
    }


}

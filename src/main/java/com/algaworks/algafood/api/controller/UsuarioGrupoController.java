package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private GrupoModelAssembler grupoModelAssembler;

    @Autowired
    private AlgaLinks algaLinks;

    @GetMapping
    public CollectionModel<GrupoModel> listar(@PathVariable("usuarioId") Long usuarioId) {
        Usuario usuario = usuarioService.buscar(usuarioId);

        Set<Grupo> grupos = usuario.getGrupos();

        CollectionModel<GrupoModel> grupoModels = grupoModelAssembler.toCollectionModel(grupos)
                .add(algaLinks.linkToGrupoUsuarioAssociacao(usuarioId, "associar"));

        grupoModels.forEach(grupoModel ->
                grupoModel.add(algaLinks.linkToGrupoUsuarioDesassociacao(usuarioId, grupoModel.getId(), "desassociar")));

        return grupoModels;
    }

    @DeleteMapping("/{grupoId}")
    public ResponseEntity<Void> desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.desassociarGrupo(usuarioId, grupoId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{grupoId}")
    public ResponseEntity<Void> associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.associarGrupo(usuarioId, grupoId);

        return ResponseEntity.noContent().build();
    }
}

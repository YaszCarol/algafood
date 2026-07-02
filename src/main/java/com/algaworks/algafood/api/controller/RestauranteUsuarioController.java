package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private UsuarioModelAssembler usuarioAssembler;
    @Autowired
    private AlgaLinks algaLinks;

    @GetMapping
    public CollectionModel<UsuarioModel> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);

        Set<Usuario> usuarios = restaurante.getUsuarios();

        CollectionModel<UsuarioModel> usuariosModel = usuarioAssembler.toCollectionModel(usuarios)
                .removeLinks()
                .add(algaLinks.linkToRestauranteUsuario(restauranteId))
                .add(algaLinks.linkToRestauranteUsuarioAssociacao(restauranteId,"associar"));

        usuariosModel.getContent().forEach(usuarioModel -> {
            usuarioModel.add(algaLinks.linkToRestauranteUsuarioDesassociacao(restauranteId,
                    usuarioModel.getId(), "desassociar"));
        });

        return usuariosModel;
    }

    @DeleteMapping("/{responsavelId}")
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
        restauranteService.desassociarResponsavel(restauranteId, responsavelId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{responsavelId}")
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
        restauranteService.associarResponsavel(restauranteId, responsavelId);
        return ResponseEntity.noContent().build();
    }

}

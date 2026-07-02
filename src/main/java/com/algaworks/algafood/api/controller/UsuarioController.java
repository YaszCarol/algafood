package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.disassembler.UsuarioDisassembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.model.input.UsuarioAtualizarInput;
import com.algaworks.algafood.api.model.input.UsuarioAtualizarSenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler usuarioAssembler;

    @Autowired
    private UsuarioDisassembler usuarioDisassembler;

    @GetMapping
    public CollectionModel<UsuarioModel> listar() {
        List<Usuario> usuarios = usuarioService.listar();

        return usuarioAssembler.toCollectionModel(usuarios);
    }

    @GetMapping("/{usuarioId}")
    public UsuarioModel buscar(@PathVariable Long usuarioId) {

        Usuario usuario = usuarioService.buscar(usuarioId);

        return usuarioAssembler.toModel(usuario);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioModel adicionar(@RequestBody @Valid UsuarioInput usuarioInput) {

        Usuario usuario = usuarioDisassembler.toDomainObject(usuarioInput);

        usuarioService.salvar(usuario);

        return usuarioAssembler.toModel(usuario);

    }

    @PutMapping("/{usuarioId}")
    public UsuarioModel atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioAtualizarInput usuarioInput) {

        Usuario usuario = usuarioService.buscar(usuarioId);

        usuarioDisassembler.copyToObject(usuarioInput, usuario);

        usuarioService.salvar(usuario);

        return usuarioAssembler.toModel(usuario);
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long usuarioId) {
        usuarioService.deletar(usuarioId);
    }

    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarSenha(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioAtualizarSenhaInput usuarioAtualizarSenhaInput) {

        usuarioService.atualizarSenha(usuarioId, usuarioAtualizarSenhaInput.getNovaSenha(), usuarioAtualizarSenhaInput.getSenhaAtual());

    }


}

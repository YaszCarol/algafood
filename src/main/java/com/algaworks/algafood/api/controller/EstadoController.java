package com.algaworks.algafood.api.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.EstadoService;

@RestController
@RequestMapping("/estados") // , produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController {

	@Autowired
	private EstadoService estadoService;

	@GetMapping
	public List<Estado> listar() {
		return estadoService.listar();
	}

	@GetMapping("/{estadoId}")
	public Estado buscar(@PathVariable Long estadoId) {
		return estadoService.buscar(estadoId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estado adicionar(@RequestBody @Valid Estado estado) {
		return estadoService.salvar(estado);
	}

	@PutMapping("/{estadoId}")
	public Estado atualizar(@PathVariable Long estadoId, @RequestBody  @Valid Estado estado) {
		Estado estadoAtual = estadoService.buscar(estadoId);

		BeanUtils.copyProperties(estado, estadoAtual, "id");

		return estadoService.salvar(estadoAtual);
	}

	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long estadoId) {
		estadoService.remover(estadoId);
	}
}

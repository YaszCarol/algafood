package com.algaworks.algafood.api.controller;

import java.util.List;

import com.algaworks.algafood.api.assembler.EstadoAssembler;
import com.algaworks.algafood.api.disassembler.EstadoDisassembler;
import com.algaworks.algafood.api.model.input.EstadoInput;
import com.algaworks.algafood.api.model.input.EstadoModel;
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

	@Autowired
	private EstadoAssembler estadoAssembler;

	@Autowired
	private EstadoDisassembler estadoDisassembler;

	@GetMapping
	public List<EstadoModel> listar() {

		List<Estado> estados = estadoService.listar();

		return estadoAssembler.toCollectModel(estados);
	}

	@GetMapping("/{estadoId}")
	public EstadoModel buscar(@PathVariable Long estadoId) {
		Estado estado = estadoService.buscar(estadoId);

		return estadoAssembler.toModel(estado);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoModel adicionar(@RequestBody @Valid EstadoInput estadoInput) {

		Estado estado = estadoDisassembler.toDomainObject(estadoInput);

		Estado estadoSalvo = estadoService.salvar(estado);

		return estadoAssembler.toModel(estadoSalvo);
	}

	@PutMapping("/{estadoId}")
	public EstadoModel atualizar(@PathVariable Long estadoId, @RequestBody  @Valid EstadoInput estadoInput) {

		Estado estadoAtual = estadoService.buscar(estadoId);

		System.out.println(estadoAtual.toString());

		estadoDisassembler.copyToObject(estadoInput, estadoAtual);

		Estado estado = estadoService.salvar(estadoAtual);

		return estadoAssembler.toModel(estado);
	}

	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long estadoId) {
		estadoService.remover(estadoId);
	}
}

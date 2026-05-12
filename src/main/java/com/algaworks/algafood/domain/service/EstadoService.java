package com.algaworks.algafood.domain.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository estadoRepository;

	private static final String MSG_ESTADO_EM_USO = "Estado de codigo %d em uso";
	private static final String MSG_ESTADO_NAO_ENCONTRADA = "Cadastro de estado com o codigo %d nao encontrado";

	public List<Estado> listar() {
		return estadoRepository.findAll();
	}

	public Estado buscar(Long estadoId) {
		return estadoRepository.findById(estadoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(
						String.format(MSG_ESTADO_NAO_ENCONTRADA, estadoId)));
	}

	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}

	public void remover(Long estadoId) {
		try {
			if (!estadoRepository.existsById(estadoId)) {
				throw new EntidadeNaoEncontradaException(
						String.format(MSG_ESTADO_NAO_ENCONTRADA, estadoId));
			}
			estadoRepository.deleteById(estadoId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_ESTADO_EM_USO, estadoId));
		}
	}
}

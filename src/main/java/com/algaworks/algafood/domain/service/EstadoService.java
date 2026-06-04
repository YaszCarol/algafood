package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    private static final String MSG_ESTADO_EM_USO = "Estado de codigo %d em uso";

    public List<Estado> listar() {
        return estadoRepository.findAll();
    }

    public Estado buscar(Long estadoId) {
        return estadoRepository.findById(estadoId)
                .orElseThrow(() -> new EstadoNaoEncontradoException(estadoId));
    }

    @Transactional
    public Estado salvar(Estado estado) {
        return estadoRepository.save(estado);
    }

    @Transactional
    public void remover(Long estadoId) {
        try {
            if (!estadoRepository.existsById(estadoId)) {
                throw new EstadoNaoEncontradoException(estadoId);
            }
            estadoRepository.deleteById(estadoId);

            // necessario pois o metodo está usando @Transactional que realiza de fato a operação (commit)
            // no fim do metodo e entao exceçoes sao lançadas pós catch.
            // estamos forçando a realizaçao da operação para que caso haja exceçoes de
            // DataIntegrityViolationException seja possível a sua captura
            estadoRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_ESTADO_EM_USO, estadoId));
        }
    }
}

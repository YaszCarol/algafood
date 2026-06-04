package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    EstadoService estadoService;

    private static final String MSG_CIDADE_EM_USO = "Cidade de codigo %d em uso";

    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }

    public Cidade buscar(Long cidadeId) {
        return cidadeRepository.findById(cidadeId).orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));
    }

    @Transactional
    public Cidade salvar(Cidade cidade) {

        Long estadoId = cidade.getEstado().getId();

        Estado estado = estadoService.buscar(estadoId);

        cidade.setEstado(estado);

        return cidadeRepository.save(cidade);
    }

    @Transactional
    public void remover(Long cidadeId) {
        if (!cidadeRepository.existsById(cidadeId)) {
            throw new CidadeNaoEncontradaException(cidadeId);
        }

        try {
            cidadeRepository.deleteById(cidadeId);
            // necessario pois o metodo está usando @Transactional que realiza de fato a operação (commit)
            // no fim do metodo e entao exceçoes sao lançadas pós catch.
            // estamos forçando a realizaçao da operação para que caso haja exceçoes de
            // DataIntegrityViolationException seja possível a sua captura
            cidadeRepository.flush();
        } catch (
                DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_CIDADE_EM_USO, cidadeId));
        }
    }
}

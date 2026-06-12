package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FormaPagamentoService {

    @Autowired
    private FormaPagamentoRepository pagamentoRepository;

    private static final String MSG_FORMAPAGAMENTO_EM_USO = "Forma de pagamento de código %d não pode ser removido, pois está em uso";

    public List<FormaPagamento> listar() {
        return pagamentoRepository.findAll();
    }

    public FormaPagamento buscar(Long formapagamentoId) {
        return pagamentoRepository.findById(formapagamentoId).
                orElseThrow(() -> new FormaPagamentoNaoEncontradaException(formapagamentoId));
    }

    @Transactional
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return pagamentoRepository.save(formaPagamento);
    }


    @Transactional
    public void deletar(Long formapagamentoId) {

        try {
            if (!pagamentoRepository.existsById(formapagamentoId)) {
                throw new FormaPagamentoNaoEncontradaException(formapagamentoId);
            }

            pagamentoRepository.deleteById(formapagamentoId);
            // necessario pois o metodo está usando @Transactional que realiza de fato a operação (commit)
            // no fim do metodo e entao exceçoes sao lançadas pós catch.
            // estamos forçando a realizaçao da operação para que caso haja exceçoes de
            // DataIntegrityViolationException seja possível a sua captura
            pagamentoRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_FORMAPAGAMENTO_EM_USO, formapagamentoId));

        }
    }
}

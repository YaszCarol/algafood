package com.algaworks.algafood.api.disassembler;

import com.algaworks.algafood.api.model.input.PagamentoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PagamentoDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamento toDomainObject(PagamentoInput pagamentoInput) {
        return modelMapper.map(pagamentoInput, FormaPagamento.class);
    }

    public void copyToObject(PagamentoInput pagamentoInput, FormaPagamento formaPagamento) {
        modelMapper.map(pagamentoInput, formaPagamento);
    }

}

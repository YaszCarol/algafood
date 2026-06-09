package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FormaPagamentoAssembler;
import com.algaworks.algafood.api.disassembler.PagamentoDisassembler;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.input.PagamentoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.FormaPagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private FormaPagamentoAssembler formaPagamentoAssembler;

    @Autowired
    private PagamentoDisassembler pagamentoDisassembler;

    @GetMapping
    public List<FormaPagamentoModel> listar() {
        List<FormaPagamento> pagamentos = formaPagamentoService.listar();

        return formaPagamentoAssembler.toCollectionModel(pagamentos);
    }

    @GetMapping("/{formapagamentoId}")
    public FormaPagamentoModel buscar(@PathVariable Long formapagamentoId) {
        FormaPagamento pagamento = formaPagamentoService.buscar(formapagamentoId);

        return formaPagamentoAssembler.toModel(pagamento);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoModel adicionar(@RequestBody @Valid PagamentoInput pagamentoInput) {

        FormaPagamento pagamento = pagamentoDisassembler.toDomainObject(pagamentoInput);

        formaPagamentoService.salvar(pagamento);

        return formaPagamentoAssembler.toModel(pagamento);
    }

    @PutMapping("/{formapagamentoId}")
    public FormaPagamentoModel atualizar(@PathVariable Long formapagamentoId, @RequestBody @Valid PagamentoInput pagamentoInput) {

        FormaPagamento formaPagamento = formaPagamentoService.buscar(formapagamentoId);

        pagamentoDisassembler.copyToObject(pagamentoInput, formaPagamento);

        formaPagamentoService.salvar(formaPagamento);

        return formaPagamentoAssembler.toModel(formaPagamento);
    }

    @DeleteMapping("/{formapagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long formapagamentoId) {
        formaPagamentoService.deletar(formapagamentoId);
    }

}

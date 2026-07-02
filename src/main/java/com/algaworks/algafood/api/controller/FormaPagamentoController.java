package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.disassembler.PagamentoDisassembler;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.input.PagamentoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.FormaPagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;

    @Autowired
    private PagamentoDisassembler pagamentoDisassembler;

    @GetMapping
    public CollectionModel<FormaPagamentoModel> listar() {
        List<FormaPagamento> pagamentos = formaPagamentoService.listar();

        return formaPagamentoModelAssembler.toCollectionModel(pagamentos);
    }

//    @GetMapping
//    public ResponseEntity<List<FormaPagamentoModel>> listar() {
//        List<FormaPagamento> pagamentos = formaPagamentoService.listar();
//
//        var formasPagamento = formaPagamentoModelAssembler.toCollectionModel(pagamentos);
//
//        return ResponseEntity.ok().
//                cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS)).
//                body(formasPagamento);
//    }

    @GetMapping("/{formapagamentoId}")
    public FormaPagamentoModel buscar(@PathVariable Long formapagamentoId, ServletWebRequest request) {

        // Deep-Etag
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";

        OffsetDateTime dataUltimaAtualizacao = formaPagamentoService.getDataAtualizacaoById(formapagamentoId);

        if (dataUltimaAtualizacao != null) {
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
        }

        if (request.checkNotModified(eTag)) {
            return null;
        }

        FormaPagamento pagamento = formaPagamentoService.buscar(formapagamentoId);

        return formaPagamentoModelAssembler.toModel(pagamento);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoModel adicionar(@RequestBody @Valid PagamentoInput pagamentoInput) {

        FormaPagamento pagamento = pagamentoDisassembler.toDomainObject(pagamentoInput);

        formaPagamentoService.salvar(pagamento);

        return formaPagamentoModelAssembler.toModel(pagamento);
    }

    @PutMapping("/{formapagamentoId}")
    public FormaPagamentoModel atualizar(@PathVariable Long formapagamentoId, @RequestBody @Valid PagamentoInput pagamentoInput) {

        FormaPagamento formaPagamento = formaPagamentoService.buscar(formapagamentoId);

        pagamentoDisassembler.copyToObject(pagamentoInput, formaPagamento);

        formaPagamentoService.salvar(formaPagamento);

        return formaPagamentoModelAssembler.toModel(formaPagamento);
    }

    @DeleteMapping("/{formapagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long formapagamentoId) {
        formaPagamentoService.deletar(formapagamentoId);
    }

}

package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.disassembler.PedidoDisassembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.core.PageWrapper;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.service.PedidoService;
import com.algaworks.algafood.infrastructure.respository.spec.PedidoSpecs;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoModelAssembler pedidoAssembler;
    private final PedidoDisassembler pedidoDisassembler;
    private final PedidoResumoModelAssembler pedidoResumoModelAssembler;
    private final PagedResourcesAssembler pagedResourcesAssembler;


    @GetMapping
    public PagedModel<PedidoResumoModel> listar(PedidoFilter filtro, Pageable pageable) {

        // necessario apenas em casos especificos
        pageable = traduzirPageable(pageable);

        Page<Pedido> pedidosPage = pedidoService.listar(PedidoSpecs.usandoFiltro(filtro), pageable);

        pedidosPage = new PageWrapper<>(pedidosPage, pageable);

        return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoModelAssembler);

    }

//    @GetMapping
//    public Page<PedidoResumoModel> listar(PedidoFilter filtro, Pageable pageable) {
//
//        // necessario apenas em casos especificos
//         pageable = traduzirPageable(pageable);
//        Page<Pedido> pedidosPage = pedidoService.listar(PedidoSpecs.usandoFiltro(filtro), pageable);
//
//        // para cada Pedido de pedidosPage.getContent aplica a funcao de converter para model
//        return pedidosPage.map(pedidoResumoModelAssembler::toModel);
//    }
//
//    @GetMapping
//    public List<PedidoResumoModel> listar() {
//        List<Pedido> pedidos = pedidoService.listar();
//
//        return pedidoResumoModelAssembler.toCollectionModel(pedidos);
//    }
//
//    @GetMapping
//    public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
//        List<Pedido> pedidos = pedidoService.listar();
//        List<PedidoResumoModel> pedidosModel = pedidoResumoModelAssembler.toCollectionModel(pedidos);
//
//        MappingJacksonValue pedidoswrapper = new MappingJacksonValue(pedidosModel);
//
//        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//        filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//
//        if(StringUtils.isNotBlank(campos)){
//            filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAllExcept(campos));
//        }
//
//        pedidoswrapper.setFilters(filterProvider);
//
//        return pedidoswrapper;
//    }

    @GetMapping("/{codigoId}")
    public PedidoModel buscar(@PathVariable String codigoId) {
        Pedido pedido = pedidoService.buscar(codigoId);

        return pedidoAssembler.toModel(pedido);
    }

    @PostMapping
    public PedidoModel adicionar(@RequestBody @Valid PedidoInput pedidoInput) {

        Pedido pedido = pedidoDisassembler.toDomainObject(pedidoInput);

        return pedidoAssembler.toModel(pedidoService.emitir(pedido));

    }

    @PutMapping("/{codigoId}/confirmacao")
    public ResponseEntity<Void> confirmar(@PathVariable String codigoId) {
        pedidoService.confirmar(codigoId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{codigoId}/cancelamento")
    public ResponseEntity<Void> cancelar(@PathVariable String codigoId) {
        pedidoService.cancelar(codigoId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{codigoId}/entregue")
    public ResponseEntity<Void> entregar(@PathVariable String codigoId) {
        pedidoService.entregar(codigoId);
        return ResponseEntity.noContent().build();
    }

    private Pageable traduzirPageable(Pageable pageable) {
        var mapeamento = Map.of(
                "codigo", "codigo",
                "subtotal", "subtotal",
                "taxaFrete", "taxaFrete",
                "valorTotal", "valorTotal",
                "dataCriacao", "dataCriacao",
                "nomerestaurante", "restaurante.nome",
                "restaurante.id", "restaurante.id",
                "cliente.id", "cliente.id",
                "cliente.nome", "cliente.nome"
        );

        return PageableTranslator.translate(pageable, mapeamento);
    }
}

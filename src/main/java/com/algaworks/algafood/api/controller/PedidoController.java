package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.disassembler.PedidoDisassembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.filter.PedidoFilter;
import com.algaworks.algafood.domain.service.PedidoService;
import com.algaworks.algafood.domain.service.RestauranteService;
import com.algaworks.algafood.infrastructure.respository.spec.PedidoSpecs;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final RestauranteService restauranteService;
    private final PedidoAssembler pedidoAssembler;
    private final PedidoDisassembler pedidoDisassembler;
    private final PedidoResumoModelAssembler pedidoResumoModelAssembler;


    @GetMapping
    public List<PedidoResumoModel> listar(PedidoFilter filtro) {
        List<Pedido> pedidos = pedidoService.listar(PedidoSpecs.usandoFiltro(filtro));

        return pedidoResumoModelAssembler.toCollectionModel(pedidos);
    }
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
    public void confirmar(@PathVariable String codigoId) {
        pedidoService.confirmar(codigoId);
    }

    @PutMapping("/{codigoId}/cancelamento")
    public void cancelar(@PathVariable String codigoId) {
        pedidoService.cancelar(codigoId);
    }

    @PutMapping("/{codigoId}/entregue")
    public void entregar(@PathVariable String codigoId) {
        pedidoService.entregar(codigoId);
    }

}

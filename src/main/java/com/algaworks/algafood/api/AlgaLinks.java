package com.algaworks.algafood.api;

import com.algaworks.algafood.api.controller.*;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import org.springframework.hateoas.*;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AlgaLinks {

    public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", VariableType.REQUEST_PARAM),
            new TemplateVariable("size", VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", VariableType.REQUEST_PARAM));

    public static final TemplateVariables PROJECAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("projecao", VariableType.REQUEST_PARAM));

    public Link linkToPedidos(String rel) {
        TemplateVariables filtroVariables = new TemplateVariables(
                new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
                new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM));

        String pedidosUrl = linkTo(PedidoController.class).toUri().toString();

        return Link.of(UriTemplate.of(pedidosUrl,
                PAGINACAO_VARIABLES.concat(filtroVariables)), rel);
    }

    public Link linkToConfirmarPedido(String codigoId, String rel) {
        return linkTo(WebMvcLinkBuilder.methodOn(PedidoController.class).
                confirmar(codigoId)).withRel(rel);
    }

    public Link linkToCancelarPedido(String codigoId, String rel) {
        return linkTo(WebMvcLinkBuilder.methodOn(PedidoController.class).
                cancelar(codigoId)).withRel(rel);
    }

    public Link linkToEntregarPedido(String codigoId, String rel) {
        return linkTo(WebMvcLinkBuilder.methodOn(PedidoController.class).
                entregar(codigoId)).withRel(rel);
    }

    public Link linkToUsuarios(Long usuarioId) {
        return linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).
                buscar(usuarioId)).withSelfRel();
    }

    public Link linkToUsuarios(String rel) {
        return linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class)
                .listar())
                .withRel(rel);
    }

    public Link linkToRestaurantes(String rel) {

        String restaurantesUrl = linkTo(RestauranteController.class).toUri().toString();

        return Link.of(UriTemplate.of(restaurantesUrl, PROJECAO_VARIABLES), rel);
    }

    public Link linkToRestauranteAtivacao(Long restauranteId, String rel) {
        return linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class)
                .ativar(restauranteId))
                .withRel(rel);
    }

    public Link linkToRestauranteInativacao(Long restauranteId, String rel) {
        return linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class)
                .inativar(restauranteId))
                .withRel(rel);
    }

    public Link linkToRestauranteAbertura(Long restauranteId, String rel) {
        return linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class)
                .abrir(restauranteId))
                .withRel(rel);
    }

    public Link linkToRestauranteFechamento(Long restauranteId, String rel) {
        return linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class)
                .fechar(restauranteId))
                .withRel(rel);
    }

    public Link linkToRestaurante(Long restauranteId) {
        return linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class).
                buscar(restauranteId)).withSelfRel();
    }

    public Link linkToFormasPagamento(Long formaPagamentoId) {
        return linkTo(FormaPagamentoController.class)
                .slash(formaPagamentoId).withSelfRel();
    }

    public Link linkToFormasPagamento(String rel) {
        return linkTo(FormaPagamentoController.class).withRel(rel);
    }

    public Link linkToFormasPagamento() {
        return linkToFormasPagamento(IanaLinkRelations.SELF.value());
    }

    public Link linkToRestauranteFormaspagamento(Long restauranteId, String rel) {
        return linkTo(WebMvcLinkBuilder.methodOn(RestauranteFormaPagamentoController.class)
                .listar(restauranteId)).withRel(rel);
    }

    public Link linkToRestauranteFormasPagamento(Long restauranteId) {
        return linkToRestauranteFormaspagamento(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestauranteFormaPagamentoDesassociacao(
            Long restauranteId, Long formaPagamentoId, String rel) {

        return linkTo(WebMvcLinkBuilder.methodOn(RestauranteFormaPagamentoController.class)
                .desassociar(restauranteId, formaPagamentoId)).withRel(rel);
    }

    public Link linkToRestauranteFormaPagamentoAssociacao(Long restauranteId, String rel) {
        return linkTo(WebMvcLinkBuilder.methodOn(RestauranteFormaPagamentoController.class)
                .associar(restauranteId, null)).withRel(rel);
    }

    public Link linkToCidade(Long cidadeId) {
        return linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class)
                .buscar(cidadeId)).withSelfRel();
    }

    public Link linkToProduto(Long produtoId, Long restauranteId, String rel) {

        return linkTo(WebMvcLinkBuilder.methodOn(
                        RestauranteProdutoController.class)
                .buscar(restauranteId, produtoId)).withRel(rel);
    }

    public Link linkToProdutos(Long restauranteId, String rel) {

        return linkTo(WebMvcLinkBuilder.methodOn(
                        RestauranteProdutoController.class)
                .listar(restauranteId, null)).withRel(rel);
    }

    public Link linkToProdutos(Long restauranteId) {

        return linkToProdutos(restauranteId, IanaLinkRelations.SELF.value());
    }

    public Link linkToFotoProduto(Long restauranteId, Long produtoId, String rel) {
        return linkTo(WebMvcLinkBuilder.methodOn(RestauranteProdutoFotoController.class)
                .buscar(restauranteId, produtoId))
                .withRel(rel);
    }

    public Link linkToFotoProduto(Long restauranteId, Long produtoId) {
        return linkToFotoProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToGrupousuario(Long usuarioId, String rel) {
        return linkTo(WebMvcLinkBuilder.methodOn(UsuarioGrupoController.class)
                .listar(usuarioId))
                .withRel(rel);
    }

    public Link linkToEstados(String rel) {
        return linkTo(WebMvcLinkBuilder.methodOn(EstadoController.class).listar()).withRel(rel);
    }

    public Link linkToEstado(Long estadoId) {
        return linkTo(WebMvcLinkBuilder.methodOn(EstadoController.class)
                .buscar(estadoId))
                .withSelfRel();
    }

    public Link linkToCozinhas(String rel) {
        return linkTo(CozinhaController.class).withRel(rel);
    }

    public Link linkToCozinhas(Long cozinhaId) {
        return linkTo(WebMvcLinkBuilder.methodOn(
                CozinhaController.class).buscar(cozinhaId)).withSelfRel();
    }

    public Link linkToCidades(String rel) {
        return linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class)
                .listar())
                .withRel(rel);
    }

    public Link linkToRestauranteUsuario(Long restauranteId) {
        return linkTo(WebMvcLinkBuilder.methodOn(
                RestauranteUsuarioController.class).listar(restauranteId)).withSelfRel();
    }

    public Link linkToRestauranteUsuario(Long restauranteId, String rel) {
        return linkTo(WebMvcLinkBuilder.methodOn(
                RestauranteUsuarioController.class).listar(restauranteId)).withRel(rel);
    }

    public Link linkToRestauranteUsuarioDesassociacao(
            Long restauranteId, Long usuarioId, String rel) {

        return linkTo(WebMvcLinkBuilder.methodOn(RestauranteUsuarioController.class)
                .desassociar(restauranteId, usuarioId)).withRel(rel);
    }

    public Link linkToRestauranteUsuarioAssociacao(Long restauranteId, String rel) {
        return linkTo(WebMvcLinkBuilder.methodOn(RestauranteUsuarioController.class)
                .associar(restauranteId, null)).withRel(rel);
    }

    public Link linkToGrupos(String rel) {
        return linkTo(GrupoController.class).withRel(rel);
    }

    public Link linkToGrupos() {
        return linkToGrupos(IanaLinkRelations.SELF.value());
    }

    public Link linkToPermissoes(String rel) {
        return linkTo(PermissaoController.class).withRel(rel);
    }

    public Link linkToPermissoes() {
        return linkToPermissoes(IanaLinkRelations.SELF.value());
    }

    public Link linkToGrupoPermissoes(Long grupoId, String rel) {
        return linkTo(WebMvcLinkBuilder.methodOn(
                        GrupoPermissaoController.class)
                .listar(grupoId)).withRel(rel);
    }

    public Link linkToGrupoPermissoes(Long grupoId) {
        return linkToGrupoPermissoes(grupoId, IanaLinkRelations.SELF.value());
    }

    public Link linkToGrupoPermissaoDesassociacao(
            Long grupoId, Long permissaoId, String rel) {

        return linkTo(methodOn(GrupoPermissaoController.class)
                .desassociar(grupoId, permissaoId)).withRel(rel);
    }

    public Link linkToGrupoPermissaoAssociacao(Long grupoId, String rel) {
        return linkTo(WebMvcLinkBuilder.methodOn(GrupoPermissaoController.class)
                .associar(grupoId, null)).withRel(rel);
    }

    public Link linkToGrupoUsuarioDesassociacao(
            Long usuarioId, Long grupoId, String rel) {

        return linkTo(methodOn(UsuarioGrupoController.class)
                .desassociar(usuarioId, grupoId)).withRel(rel);
    }

    public Link linkToGrupoUsuarioAssociacao(Long usuarioId, String rel) {
        return linkTo(WebMvcLinkBuilder.methodOn(UsuarioGrupoController.class)
                .associar(usuarioId, null)).withRel(rel);
    }

    public Link linkToEstatisticas(String rel) {
        return linkTo(EstatisticasController.class).withRel(rel);
    }

    public Link linkToEstatisticasVendasDiarias(String rel) {
        TemplateVariables filtroVariables = new TemplateVariables(
                new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacao", VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM),
                new TemplateVariable("timeOffset", VariableType.REQUEST_PARAM));

        String estatisticasUrl = linkTo(EstatisticasController.class).toUri().toString();

        return Link.of(UriTemplate.of(estatisticasUrl,
                filtroVariables), IanaLinkRelations.SELF.value());
    }
}
package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.model.*;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AlgaLinks algaLinks;

    public RestauranteModelAssembler() {
        super(RestauranteController.class, RestauranteModel.class);
    }

    @Override
    public RestauranteModel toModel(Restaurante restaurante) {

        RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);

        modelMapper.map(restaurante, restauranteModel);

        CozinhaModel cozinha = restauranteModel.getCozinha();

        Optional.ofNullable(restauranteModel.getEndereco())
                .map(EnderecoModel::getCidade)
                .ifPresent(cidadeResumoModel ->
                        cidadeResumoModel.add(algaLinks.linkToCidade(cidadeResumoModel.getId())));

        cozinha.add(algaLinks.linkToCozinhas(cozinha.getId()));
        restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));
        restauranteModel.add(algaLinks.linkToRestauranteFormaspagamento(restauranteModel.getId(), "formas-pagamento"));
        restauranteModel.add(algaLinks.linkToRestauranteUsuario(restauranteModel.getId(), "responsaveis"));
        restauranteModel.add(algaLinks.linkToProdutos(restaurante.getId(), "produtos"));

        if (restaurante.inativacaoPermitida())
            restauranteModel.add(algaLinks.linkToRestauranteInativacao(restauranteModel.getId(), "inativar"));

        if (restaurante.ativacaoPermitida())
            restauranteModel.add(algaLinks.linkToRestauranteAtivacao(restauranteModel.getId(), "ativar"));

        if (restaurante.fechamentoPermitido())
            restauranteModel.add(algaLinks.linkToRestauranteFechamento(restauranteModel.getId(), "fechar"));

        if (restaurante.aberturaPermitida())
            restauranteModel.add(algaLinks.linkToRestauranteAbertura(restauranteModel.getId(), "abrir"));


        return restauranteModel;
    }
}

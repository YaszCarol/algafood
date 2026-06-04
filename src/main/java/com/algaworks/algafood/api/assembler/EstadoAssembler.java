package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.EstadoIdInput;
import com.algaworks.algafood.api.model.input.EstadoInput;
import com.algaworks.algafood.api.model.input.EstadoModel;
import com.algaworks.algafood.domain.model.Estado;
import jakarta.persistence.Column;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstadoAssembler {

    @Autowired
    private ModelMapper mapper;

    public EstadoModel toModel(Estado estado) {
        return mapper.map(estado, EstadoModel.class);
    }

    public List<EstadoModel> toCollectModel(List<Estado> estados) {
        return estados.stream()
                .map(this::toModel).
                collect(Collectors.toList());
    }

}

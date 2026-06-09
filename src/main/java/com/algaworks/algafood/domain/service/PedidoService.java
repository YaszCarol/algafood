package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public List<Pedido> listar(){
        return pedidoRepository.findAll();
    }
    public Pedido buscar(Long pedidoId){
        return pedidoRepository.findById(pedidoId).orElseThrow(()-> new PedidoNaoEncontradoException(pedidoId));
    }
}

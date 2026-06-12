package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Produto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends CustomJpaRepository<Produto, Long> {

    public Optional<Produto> findByIdAndRestauranteId(Long produtoId, Long restauranteid);
}

package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.algaworks.algafood.domain.model.Cozinha;

import java.time.OffsetDateTime;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

    @Query("""
                select f.dataAtualizacao
                from FormaPagamento f
                where f.id = :id
            """)
    OffsetDateTime getDataAtualizacaoById(Long id);
}


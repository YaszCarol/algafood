package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Produto {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private  String nome;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private Boolean ativo;

    @Column(nullable = false)
    private BigDecimal preco;

    // Varios produtos com o mesmo ID de restaurante
    @ManyToOne // muitos (produtos) para um (restaurante)
    @JoinColumn(nullable = false)
    private Restaurante restaurante;
}

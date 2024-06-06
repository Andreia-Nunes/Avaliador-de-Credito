package dev.andreia.msavaliadorcredito.domain;

import lombok.Data;

@Data
public class Cliente {
    private Long id;
    private String nome;
    private Integer idade;
}

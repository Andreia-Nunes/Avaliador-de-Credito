package dev.andreia.msavaliadorcredito.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Cartao {
    private String nomeCartao;
    private String bandeira;
    private BigDecimal limiteBase;
}

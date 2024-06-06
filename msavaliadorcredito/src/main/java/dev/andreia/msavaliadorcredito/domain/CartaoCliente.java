package dev.andreia.msavaliadorcredito.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoCliente {
    private String nomeCartao;
    private String bandeira;
    private BigDecimal limiteAtual;
}

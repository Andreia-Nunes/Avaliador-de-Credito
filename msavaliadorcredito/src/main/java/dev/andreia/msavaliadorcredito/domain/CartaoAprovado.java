package dev.andreia.msavaliadorcredito.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CartaoAprovado {
    private String nomeCartao;
    private String bandeira;
    private BigDecimal limiteAprovado;
}

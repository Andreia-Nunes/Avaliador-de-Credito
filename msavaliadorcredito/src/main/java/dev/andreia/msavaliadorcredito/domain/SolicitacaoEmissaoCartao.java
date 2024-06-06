package dev.andreia.msavaliadorcredito.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SolicitacaoEmissaoCartao {
    private Long idCartao;
    private String cpfCliente;
    private String enderecoCliente;
    private BigDecimal limiteLiberado;
}

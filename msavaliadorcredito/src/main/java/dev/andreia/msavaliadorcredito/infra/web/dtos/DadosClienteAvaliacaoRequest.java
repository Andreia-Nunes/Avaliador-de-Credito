package dev.andreia.msavaliadorcredito.infra.web.dtos;

import lombok.Data;

@Data
public class DadosClienteAvaliacaoRequest {
    private String cpf;
    private Long renda;
}

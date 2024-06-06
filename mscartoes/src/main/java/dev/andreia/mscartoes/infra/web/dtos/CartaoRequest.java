package dev.andreia.mscartoes.infra.web.dtos;

import dev.andreia.mscartoes.domain.Cartao;
import dev.andreia.mscartoes.domain.enums.BandeiraCartao;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoRequest {

    private String nomeCartao;
    private BandeiraCartao bandeira;
    private BigDecimal rendaMinimaNecessaria;
    private BigDecimal limiteBase;

    public Cartao toModel(){
        return new Cartao(this.nomeCartao, this.bandeira, this.rendaMinimaNecessaria, this.limiteBase);
    }
}

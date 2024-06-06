package dev.andreia.mscartoes.domain;

import dev.andreia.mscartoes.domain.enums.BandeiraCartao;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeCartao;

    @Enumerated(EnumType.STRING)
    private BandeiraCartao bandeira;
    private BigDecimal rendaMinimaNecessaria;
    private BigDecimal limiteBase;

    public Cartao(String nomeCartao, BandeiraCartao bandeira, BigDecimal rendaMinimaNecessaria, BigDecimal limiteBase){
        this.nomeCartao = nomeCartao;
        this.bandeira = bandeira;
        this.rendaMinimaNecessaria = rendaMinimaNecessaria;
        this.limiteBase = limiteBase;
    }
}

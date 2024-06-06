package dev.andreia.mscartoes.infra.web.dtos;

import dev.andreia.mscartoes.domain.ClienteCartao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteCartaoResponse {

    private String nomeCartao;
    private String bandeira;
    private BigDecimal limiteAtual;

    public static ClienteCartaoResponse fromModel(ClienteCartao model){
        return new ClienteCartaoResponse(model.getCartao().getNomeCartao(),
                model.getCartao().getBandeira().toString(),
                model.getLimiteAtual());
    }
}

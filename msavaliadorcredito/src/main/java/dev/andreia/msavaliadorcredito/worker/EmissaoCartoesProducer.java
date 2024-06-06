package dev.andreia.msavaliadorcredito.worker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.andreia.msavaliadorcredito.domain.SolicitacaoEmissaoCartao;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmissaoCartoesProducer {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queueEmissaoCartoes;

    public void sendMessageEmissaoCartoesQueue(SolicitacaoEmissaoCartao dados) throws JsonProcessingException {
        String json = this.convertToJson(dados);
        rabbitTemplate.convertAndSend(queueEmissaoCartoes.getName(), json);
    }

    private String convertToJson(SolicitacaoEmissaoCartao dados) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dados);
        return json;
    }

}

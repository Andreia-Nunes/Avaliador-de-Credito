package dev.andreia.mscartoes.infra.worker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.andreia.mscartoes.domain.Cartao;
import dev.andreia.mscartoes.domain.ClienteCartao;
import dev.andreia.mscartoes.domain.SolicitacaoEmissaoCartao;
import dev.andreia.mscartoes.infra.repository.CartaoRepository;
import dev.andreia.mscartoes.infra.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmissaoCartoesConsumer {

    private final CartaoRepository cartaoRepository;
    private final ClienteCartaoRepository clienteCartaoRepository;

    @RabbitListener(queues = "${mq.queue.emissao-cartoes}")
    public void receberSolicitacaoEmissaoCartao(@Payload String payload){
        this.tratarSolicitacaoEmissaoCartao(payload);
    }

    private void tratarSolicitacaoEmissaoCartao(String payload){
        try{
            SolicitacaoEmissaoCartao solicitacaoEmissaoCartao = this.convertToObject(payload);

            Cartao cartao = this.cartaoRepository
                    .findById(solicitacaoEmissaoCartao.getIdCartao())
                    .orElseThrow();

            ClienteCartao clienteCartao = new ClienteCartao();
            clienteCartao.setCpf(solicitacaoEmissaoCartao.getCpfCliente());
            clienteCartao.setCartao(cartao);
            clienteCartao.setLimiteAtual(solicitacaoEmissaoCartao.getLimiteLiberado());

            clienteCartaoRepository.save(clienteCartao);

        }
        catch (Exception e){
            log.error("Erro ao receber solicitação de emissão de cartão", e);
        }
    }

    private SolicitacaoEmissaoCartao convertToObject(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, SolicitacaoEmissaoCartao.class);
    }
}

package dev.andreia.msavaliadorcredito.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.andreia.msavaliadorcredito.application.exceptions.ClienteNotFoundException;
import dev.andreia.msavaliadorcredito.application.exceptions.ErroComunicacaoMicroservicesException;
import dev.andreia.msavaliadorcredito.application.exceptions.ErroSolicitacaoCartaoException;
import dev.andreia.msavaliadorcredito.domain.*;
import dev.andreia.msavaliadorcredito.infra.feignClient.MsCartoesClient;
import dev.andreia.msavaliadorcredito.infra.feignClient.MsClienteClient;
import dev.andreia.msavaliadorcredito.worker.EmissaoCartoesProducer;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final MsClienteClient msClienteClient;
    private final MsCartoesClient msCartoesClient;
    private final EmissaoCartoesProducer producer;

    public SituacaoCliente getSituacaoCliente(String cpf) throws ClienteNotFoundException, ErroComunicacaoMicroservicesException {
        try{
            ResponseEntity<Cliente> responseCliente = this.msClienteClient.getCliente(cpf);
            ResponseEntity<List<CartaoCliente>> responseCartoes = this.msCartoesClient.findByCpf(cpf);

            return SituacaoCliente
                    .builder()
                    .cliente(responseCliente.getBody())
                    .cartoes(responseCartoes.getBody())
                    .build();
        } catch(FeignException.FeignClientException e){
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value() == status){
                throw new ClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }

    }

    public List<CartaoAprovado> realizarAvaliacao(String cpf, Long renda) throws ClienteNotFoundException, ErroComunicacaoMicroservicesException {
        try{
            ResponseEntity<Cliente> responseCliente = this.msClienteClient.getCliente(cpf);
            Cliente cliente = responseCliente.getBody();

            ResponseEntity<List<Cartao>> responseCartoes = this.msCartoesClient.getByRendaMinimaNecessariaLessThanEqual(renda);
            List<Cartao> cartoes = responseCartoes.getBody();

            List<CartaoAprovado> cartoesAprovados = cartoes
                    .stream()
                    .map(cartao -> this.criarCartaoAprovado(cliente.getIdade(), cartao))
                    .collect(Collectors.toList());

            return cartoesAprovados;
        }
        catch (FeignException.FeignClientException e){
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value() == status){
                throw new ClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }
    }

    private CartaoAprovado criarCartaoAprovado(Integer idade, Cartao cartao) {
        CartaoAprovado cartaoAprovado = CartaoAprovado
                .builder()
                .nomeCartao(cartao.getNomeCartao())
                .bandeira(cartao.getBandeira())
                .limiteAprovado(this.calcularLimiteAprovado(idade, cartao.getLimiteBase()))
                .build();

        return cartaoAprovado;
    }

    private BigDecimal calcularLimiteAprovado(Integer idade, BigDecimal limiteBase) {
        return limiteBase.multiply(BigDecimal.valueOf(idade).divide(BigDecimal.TEN));
    }

    public String solicitarEmissaoCartao(SolicitacaoEmissaoCartao solicitacaoEmissaoCartao) throws ErroSolicitacaoCartaoException {
        try {
            this.producer.sendMessageEmissaoCartoesQueue(solicitacaoEmissaoCartao);
            return UUID.randomUUID().toString();
        } catch (Exception e) {
            throw new ErroSolicitacaoCartaoException(e.getMessage());
        }

    }

}

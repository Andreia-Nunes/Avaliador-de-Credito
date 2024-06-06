package dev.andreia.msavaliadorcredito.infra.web;

import dev.andreia.msavaliadorcredito.application.AvaliadorCreditoService;
import dev.andreia.msavaliadorcredito.application.exceptions.ClienteNotFoundException;
import dev.andreia.msavaliadorcredito.application.exceptions.ErroComunicacaoMicroservicesException;
import dev.andreia.msavaliadorcredito.application.exceptions.ErroSolicitacaoCartaoException;
import dev.andreia.msavaliadorcredito.domain.CartaoAprovado;
import dev.andreia.msavaliadorcredito.domain.SituacaoCliente;
import dev.andreia.msavaliadorcredito.domain.SolicitacaoEmissaoCartao;
import dev.andreia.msavaliadorcredito.infra.web.dtos.DadosClienteAvaliacaoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes-credito")
@RequiredArgsConstructor
public class AvaliadorCreditoController {

    private final AvaliadorCreditoService service;

    @GetMapping
    public String status(){
        return "ok";
    }

    @GetMapping(value = "/situacao-cliente", params = "cpf")
    public ResponseEntity<Object> getSituacaoCliente(@RequestParam("cpf") String cpf){
        try{
            SituacaoCliente situacaoCliente = this.service.getSituacaoCliente(cpf);
            return ResponseEntity.ok(situacaoCliente);
        }
        catch (ClienteNotFoundException e){
            return ResponseEntity.notFound().build();
        }
        catch (ErroComunicacaoMicroservicesException e){
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }

    }

    @PostMapping
    public ResponseEntity<Object> realizarAvaliacao(@RequestBody DadosClienteAvaliacaoRequest dadosClienteAvaliacao){
        try{
            List<CartaoAprovado> cartoesAprovados = this.service.realizarAvaliacao(
                    dadosClienteAvaliacao.getCpf(),
                    dadosClienteAvaliacao.getRenda()
            );
             return ResponseEntity.ok(cartoesAprovados);
        }
        catch(ClienteNotFoundException e){
            return ResponseEntity.notFound().build();
        }
        catch(ErroComunicacaoMicroservicesException e){
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }

    @PostMapping("/solicitacao-cartao")
    public ResponseEntity<Object> solicitarEmissaoCartao(@RequestBody SolicitacaoEmissaoCartao solicitacaoEmissaoCartao){
        try {
            String protocolo = this.service.solicitarEmissaoCartao(solicitacaoEmissaoCartao);
            return ResponseEntity.ok(protocolo);
        } catch (ErroSolicitacaoCartaoException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}

package dev.andreia.mscartoes.infra.web;

import dev.andreia.mscartoes.application.CartaoService;
import dev.andreia.mscartoes.application.ClienteCartaoService;
import dev.andreia.mscartoes.domain.Cartao;
import dev.andreia.mscartoes.domain.ClienteCartao;
import dev.andreia.mscartoes.infra.web.dtos.CartaoRequest;
import dev.andreia.mscartoes.infra.web.dtos.ClienteCartaoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cartoes")
@RequiredArgsConstructor
public class CartoesController {

    private final CartaoService cartaoService;
    private final ClienteCartaoService clienteCartaoService;

    @GetMapping
    public String status(){
        return "ok";
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody CartaoRequest request){
        Cartao cartao = request.toModel();
        this.cartaoService.save(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getByRendaMinimaNecessariaLessThanEqual(@RequestParam("renda") Long renda){
        List<Cartao> cartoes = this.cartaoService.getByRendaMinimaNecessariaLessThanEqual(renda);
        return ResponseEntity.ok(cartoes);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<ClienteCartaoResponse>> findByCpf(@RequestParam("cpf") String cpf){
        List<ClienteCartao> clienteCartaoList = this.clienteCartaoService.findByCpf(cpf);
        List<ClienteCartaoResponse> clienteCartaoResponseList = clienteCartaoList
                .stream()
                .map(ClienteCartaoResponse::fromModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(clienteCartaoResponseList);
    }

}

package dev.andreia.msavaliadorcredito.infra.feignClient;

import dev.andreia.msavaliadorcredito.domain.Cartao;
import dev.andreia.msavaliadorcredito.domain.CartaoCliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "mscartoes", path = "/cartoes")
public interface MsCartoesClient {

    @GetMapping(params = "cpf")
    ResponseEntity<List<CartaoCliente>> findByCpf(@RequestParam String cpf);

    @GetMapping(params = "renda")
    ResponseEntity<List<Cartao>> getByRendaMinimaNecessariaLessThanEqual(@RequestParam("renda") Long renda);
}

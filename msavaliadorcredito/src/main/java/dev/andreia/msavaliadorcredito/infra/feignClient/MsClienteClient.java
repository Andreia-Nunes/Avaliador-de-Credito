package dev.andreia.msavaliadorcredito.infra.feignClient;

import dev.andreia.msavaliadorcredito.domain.Cliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "msclientes", path = "/clientes")
public interface MsClienteClient {

    @GetMapping(params = "cpf")
    ResponseEntity<Cliente> getCliente(@RequestParam String cpf);
}

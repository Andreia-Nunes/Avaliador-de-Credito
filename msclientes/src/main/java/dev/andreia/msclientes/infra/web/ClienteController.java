package dev.andreia.msclientes.infra.web;

import dev.andreia.msclientes.application.ClienteService;
import dev.andreia.msclientes.domain.Cliente;
import dev.andreia.msclientes.infra.web.dtos.ClienteRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Slf4j
public class ClienteController {

    private final ClienteService service;

    @GetMapping
    public String status(){
        log.info("Obtendo o status do microservice de clientes.");
        return "ok";
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ClienteRequest request){
        Cliente cliente = request.toModel();
        this.service.save(cliente);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .query("cpf={cpf}")
                .buildAndExpand(cliente.getCpf())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(params = "cpf")
    public ResponseEntity getByCpf(@RequestParam String cpf){
        Optional<Cliente> cliente = this.service.getByCpf(cpf);

        if(cliente.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cliente.get());
    }

}

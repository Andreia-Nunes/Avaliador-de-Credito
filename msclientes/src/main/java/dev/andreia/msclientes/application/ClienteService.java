package dev.andreia.msclientes.application;

import dev.andreia.msclientes.domain.Cliente;
import dev.andreia.msclientes.infra.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    @Transactional
    public Cliente save(Cliente cliente){
        return this.repository.save(cliente);
    }

    public Optional<Cliente> getByCpf(String cpf){
        return this.repository.findByCpf(cpf);
    }
}

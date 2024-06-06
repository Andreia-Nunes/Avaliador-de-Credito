package dev.andreia.mscartoes.application;

import dev.andreia.mscartoes.domain.ClienteCartao;
import dev.andreia.mscartoes.infra.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {

    private final ClienteCartaoRepository repository;

    public List<ClienteCartao> findByCpf(String cpf){
        return this.repository.findByCpf(cpf);
    }
}

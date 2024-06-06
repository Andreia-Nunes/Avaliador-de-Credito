package dev.andreia.mscartoes.application;

import dev.andreia.mscartoes.domain.Cartao;
import dev.andreia.mscartoes.infra.repository.CartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartaoService {

    private final CartaoRepository repository;

    @Transactional
    public Cartao save(Cartao cartao){
        return this.repository.save(cartao);
    }

    public List<Cartao> getByRendaMinimaNecessariaLessThanEqual(Long renda){
        BigDecimal rendaBigDecimal = BigDecimal.valueOf(renda);
        return this.repository.findByRendaMinimaNecessariaLessThanEqual(rendaBigDecimal);
    }
}

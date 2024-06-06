package dev.andreia.msclientes.infra.web.dtos;

import dev.andreia.msclientes.domain.Cliente;
import lombok.Data;

@Data
public class ClienteRequest {

    private String nome;
    private String cpf;
    private Integer idade;

    public Cliente toModel(){
        return new Cliente(this.nome, this.cpf,this.idade);
    }
}

package dev.andreia.msavaliadorcredito.application.exceptions;

public class ClienteNotFoundException extends Exception{

    public ClienteNotFoundException(){
        super("Cliente não encontrado com o cpf informado.");
    }
}

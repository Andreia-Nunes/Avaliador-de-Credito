
# Sistema avaliador de crédito

## Descrição do projeto:
<p>Este projeto trata-se de um sistema de avaliação de crédito que oferece uma variedade de endpoints para gerenciar clientes e cartões.</p>

<p>Foi construído utilizando-se de uma arquitetura de microserviços que se comunicam entre si através de API's REST e mensageria, de forma síncrona e assíncrona, respectivamente. A arquitetura consta com um Service Registry para registro e descoberta das instâncias dos serviços. Além disso, foi implementado um API Gateway que atua como intermediário entre os clientes e as aplicações, servindo como um centralizador e distribuidor de requisições, permitindo também o balanceamento de carga através do seu Load Balancer. </p>

<p>Todo o sistema foi Dockerizado, portanto, pode ser executado localmente através de contêineres, gerenciados pelo Docker Compose.</p>
<br/>


## Documentação da API:

<p> Todas as requisições devem ser enviadas de maneira centralizada para o Gateway, que realizará o devido roteamento. Para execução local, deve-se utilizar a URL <b>http://localhost:8080</b>, seguida por um dos endpoints especificados abaixo:</p>

| Endpoint                                | Query Param | Método | Descrição                                                              | Exemplo                                                          |
|-----------------------------------------|-------------|--------|------------------------------------------------------------------------|-----------------------------------------------------------------|
| `/clientes`                             | cpf         | GET    | Obtém detalhes de um cliente por CPF                                   | `GET /clientes?cpf=01234567890`                                        |
| `/clientes`                             |             | POST   | Cria um novo cliente                                                   | `POST /clientes` com corpo JSON (modelo 1)                              |
| `/cartoes`                              | cpf         | GET    | Obtém a lista de todos cartões de um cliente                           | `GET /cartoes?cpf=01234567890`                                                 |
| `/cartoes`                              | renda       | GET    | Obtém a lista de todos cartões elegíveis para uma determinada renda    | `GET /cartoes?renda=5000`                                                      |
| `/cartoes`                              |             | POST   | Cria um novo cartão                                                    | `POST /cartoes` com corpo JSON (modelo 2)                               |
| `/avaliacoes-credito`                   |             | POST   | Obtém a lista de todos os cartões de crédito elegíveis para um cliente | `POST /avaliacoes-credito` com corpo JSON (modelo 3)                    |
| `/avaliacoes-credito/situacao-cliente`  | cpf         | GET    | Obtém detalhes de um cliente, juntamente com todos os seus cartões     | `GET /avaliacoes-credito/situacao-cliente?cpf=01234567890`             |
| `/avaliacoes-credito/solicitacao-cartao`|             | POST   | Solicita cartão para um cliente                                        | `POST /avaliacoes-credito/solicitacao-cartao` com corpo JSON (modelo 4) |
<br/>

### Modelos JSON: 

#### Modelo 1:
```json
{
    "nome" : "Maria da Silva",
    "cpf" : 01234567890,
    "idade" : 27
}
```

#### Modelo 2:
```json
{
	"nomeCartao": "Bradesco Master",
	"bandeira": "MASTERCARD",
	"rendaMinimaNecessaria": 5000,
	"limiteBase": 8000
}
```

#### Modelo 3:
```json
{
	"cpf" : "01234567890",
	"renda" : 5000
}
```

#### Modelo 4:
```json
{
	"idCartao" : 1,
	"cpfCliente" : "01234567890",
	"enderecoCliente" : "Rua X",
	"limiteLiberado" : 20000
}
```
<br/>


### Tecnologias utilizadas:
[![Java Version](https://img.shields.io/badge/Java-11-blue)](https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html)
[![Spring Boot Version](https://img.shields.io/badge/Spring%20Boot-2.6.x-brightgreen)](https://docs.spring.io/spring-boot/docs/2.6.0/reference/html/)
[![Spring Web](https://img.shields.io/badge/Spring%20Web-brightgreen)](https://docs.spring.io/spring-boot/reference/web/index.html)
[![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-brightgreen)](https://docs.spring.io/spring-boot/docs/2.6.0/reference/html/data.html#data.sql.jpa-and-spring-data)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-brightgreen)](https://spring.io/projects/spring-security)
[![Spring Cloud OpenFeign](https://img.shields.io/badge/Spring%20Cloud%20OpenFeign-brightgreen)](https://spring.io/projects/spring-cloud-openfeign)
[![Spring Cloud Gateway](https://img.shields.io/badge/Spring%20Cloud%20Gateway-blue)](https://spring.io/projects/spring-cloud-gateway)
[![Spring AMQP](https://img.shields.io/badge/Spring%20AMQP-brightgreen)](https://spring.io/projects/spring-amqp)
[![Spring DevTools](https://img.shields.io/badge/Spring%20DevTools-brightgreen)](https://docs.spring.io/spring-boot/reference/using/devtools.html)
[![H2 Database](https://img.shields.io/badge/H2%20Database-blue)](https://h2database.com/html/main.html)
[![Lombok](https://img.shields.io/badge/Lombok-red)](https://projectlombok.org/)
[![Netflix Eureka Server](https://img.shields.io/badge/Netflix%20Eureka%20Server-red)](https://cloud.spring.io/spring-cloud-netflix/reference/html/)
[![RabbitMQ](https://img.shields.io/badge/RabbitMQ-orange)](https://www.rabbitmq.com/)
[![Docker](https://img.shields.io/badge/Docker-blue)](https://www.docker.com/)
[![Docker Compose](https://img.shields.io/badge/Docker%20Compose-blue)](https://docs.docker.com/compose/)
<br/>
<br/>

### Executando o projeto localmente:

#### Requisitos:
- Git
- Docker
- Postman ou Insominia

#### Passo-a-passo:
1. Clone o repositório do Github
```bash
git clone https://github.com/Andreia-Nunes/Avaliador-de-Credito.git
```
2. Abra o Terminal e navegue até o diretório raiz do projeto, onde estará localizado o arquivo <i>docker-compose.yaml</i>

3. Execute o seguinte comando:
```bash
docker-compose up -d
```

4. Prontinho, agora já pode enviar as requisições HTTP!

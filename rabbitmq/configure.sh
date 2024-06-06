#!/bin/bash

# Aguarde o RabbitMQ iniciar completamente
sleep 10

# Declare a fila 'emissao-cartoes' usando rabbitmqctl
rabbitmqctl add_vhost /
rabbitmqctl set_permissions -p / guest ".*" ".*" ".*"
rabbitmqctl set_policy -p / "ha-all" "^" '{"ha-mode":"all"}'
rabbitmqctl eval 'rabbit_amqqueue:declare({resource, <<"/">>, queue, <<"emissao-cartoes">>}, true, false, [], none, "").'

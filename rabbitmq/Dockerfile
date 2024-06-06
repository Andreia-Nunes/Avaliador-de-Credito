# Use a imagem base do RabbitMQ com o management plugin
FROM rabbitmq:3.9-management

# Copie o script de configuração para dentro do container
COPY configure.sh /usr/local/bin/configure.sh

# Dê permissão de execução ao script
RUN chmod +x /usr/local/bin/configure.sh

# Execute o script de configuração na inicialização do RabbitMQ
CMD ["sh", "-c", "rabbitmq-server & sleep 10 && /usr/local/bin/configure.sh && tail -f /dev/null"]

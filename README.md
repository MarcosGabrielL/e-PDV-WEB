#Para Iniciar o KAFKA

## Obter KAFKA
Baixe a versão mais recente do Kafka e extraia-a:

$ tar -xzf kafka_2.13-3.2.0.tgz
$ cd kafka_2.13-3.2.0

## Iniciar o Ambiente KAFKA

NOTA: Seu ambiente local deve ter o Java 8+ instalado.

Execute os seguintes comandos para iniciar todos os serviços na ordem correta:


// Start the ZooKeeper service
$ bin/zookeeper-server-start.sh config/zookeeper.properties

Abra outra sessão de terminal e execute:

// Start the Kafka broker service
$ bin/kafka-server-start.sh config/server.properties


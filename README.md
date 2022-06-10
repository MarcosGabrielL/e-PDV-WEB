## e-GDV 5.0 Web

- Realtime web notificações e chat Usando Apache Kafka Websocket
- Processamento assíncrono de Reqisições HTTP e AMQP com RabbitMQ para processamento de vendas ( Enviar e Receber NFe/XML com as informações fiscais transmitidas para a SEFAZ)
- Nfe/XML assinado digitalmente baseado no pacote javax.xml.crypto.dsig para garantir a integridade dos dados e comprovar a autoria de seu emissor.
- Java Persistence API com Hibernet para banco de dados PostgreSQL ou MySQL
- Test-Driven Development (TDD) com JUnit para testes unitários, JMeter para teste de carga/desempenho e Mockito para Rest API Testing
- Cross Site Request Forgery (CSRF), Security HTTP Response Headers, HTTP e HTTP Firewall protegidos pelo Spring Security
- Sistema virtualizado com docker
- Kubernetes para orquestrar clusters dos containers
- Gerenciamento de LOG com ELK
- JasperReports para geração de Cupom Fiscal e Nota Fiscal como relatórios para Salvar como PDF e imprimir em Impressora Termica

### Inicie o zookeeper e o kafka instance com

```ruby
docker-compose up -d
```

### Baixando e instalando o RabbitMQ coma imagem do Docker 

```ruby
 # latest RabbitMQ 3.10
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.10-management
```

### No Diretorio do aplicativo use os comandos abaixo para compilar e executar o aplicativo:

```ruby
docker build -t springio/gs-spring-boot-docker .
docker run -p 8080:8080 springio/gs-spring-boot-docker
```

### docker-compose

```ruby
version: '3'


services:
  zookeeper:
    image: confluentinc/cp-zookeeper:7.0.1
    container_name: zookeeper
    environment:
      ZOOKEEPER_CLIENT_PORT: 8081
      ZOOKEEPER_TICK_TIME: 2000

  broker:
    image: confluentinc/cp-kafka:7.0.1
    container_name: broker
    ports:
    # To learn about configuring Kafka for access across networks see
    # https://www.confluent.io/blog/kafka-client-cannot-connect-to-broker-on-aws-on-docker-etc/
      - "9092:9092"
    depends_on:
      - zookeeper
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: 'zookeeper:2181'
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_INTERNAL:PLAINTEXT
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092,PLAINTEXT_INTERNAL://broker:29092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_TRANSACTION_STATE_LOG_MIN_ISR: 1
      KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: 1

 db-mysql:
    image: mysql:5.7
    container_name: db-mysql
    environment:
      MYSQL_ROOT_PASSWORD: root
    ports:
      - "3306:3306"
  db-psql:
    image: postgres:9
    container_name: db-postgres
    environment:
      POSTGRES_USER: root
      POSTGRES_PASSWORD: root
    ports:
      - "5432:5432"

 rabbitmq:
    image: rabbitmq:3-management-alpine
    container_name: 'rabbitmq'
    ports:
        - 5672:5672
        - 15672:15672
    volumes:
        - ~/.docker-conf/rabbitmq/data/:/var/lib/rabbitmq/
        - ~/.docker-conf/rabbitmq/log/:/var/log/rabbitmq
    networks:
        - rabbitmq_go_net

 networks:
  rabbitmq_go_net:
    driver: bridge
```


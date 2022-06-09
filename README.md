## e-GDV 5.0 Web

- Realtime web notificações e chat Usando Apache Kafka Websocket
- Processamento assíncrono de Reqisições HTTP e AMQP com RabbitMQ para processamento de vendas ( Enviar e Receber NFe/XML com as informações fiscais transmitidas para a SEFAZ)
- Nfe/XML assinado digitalmente baseado no pacote javax.xml.crypto.dsig para garantir a integridade dos dados e comprovar a autoria de seu emissor.
- Java Persistence API com Hibernet para banco de dados PostgreSQL ou MySQL
- Test-Driven Development (TDD) com JUnit para testes unitários, JMeter para teste de carga/desempenho e Mockito para Rest API Testing
- Cross Site Request Forgery (CSRF), Security HTTP Response Headers, HTTP e HTTP Firewall protegidos pelo Spring Security

### Inicie o zookeeper e o kafka instance com

```ruby
docker-compose up -d
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
  springboot-docker-compose-app-container:
    image: springboot-docker-compose-app:1
    build:
      context: ./
      dockerfile: Dockerfile
    volumes:
      - /data/egdvweb
    ports:
      - "8081:8081"
      
  zoo1:
    image: zookeeper:3.4.9
    hostname: zoo1
    ports:
      - "2181:2181"  
    environment:
        ZOO_MY_ID: 1
        ZOO_PORT: 2181
        ZOO_SERVERS: server.1=zoo1:2888:3888
kafka1:
    image: confluentinc/cp-kafka:5.5.1
    hostname: kafka1
    ports:
      - "9092:9092"
      - "29092:29092"      
    environment:
      KAFKA_ADVERTISED_LISTENERS: LISTENER_DOCKER_INTERNAL://127.0.0.1:29092,LISTENER_DOCKER_EXTERNAL://127.0.0.1:9092
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: LISTENER_DOCKER_INTERNAL:PLAINTEXT,LISTENER_DOCKER_EXTERNAL:PLAINTEXT
      KAFKA_INTER_BROKER_LISTENER_NAME: LISTENER_DOCKER_INTERNAL
      KAFKA_ZOOKEEPER_CONNECT: "zoo1:2181"
      KAFKA_CREATE_TOPICS: notification:1:1:compact
      KAFKA_BROKER_ID: 1
      KAFKA_LOG4J_LOGGERS: "kafka.controller=INFO,kafka.producer.async.DefaultEventHandler=INFO,state.change.logger=INFO"
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
    depends_on:
      - zoo1
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
```


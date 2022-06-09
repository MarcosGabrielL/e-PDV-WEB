/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Kafka.Services;

import com.softsaj.egdvweb.Kafka.Configuração.KafkaProducerConfig;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
/**
 *
 * @author Marcos
 */
@Slf4j
public class BrokerProducerService {

   KafkaProducerConfig KafkaProducerConfig = new KafkaProducerConfig();
    
    private  KafkaTemplate<Integer, String> kafkaTemplate = KafkaProducerConfig.kafkaTemplate();

    

    public void sendNotification(String topic, String message) {
        // the KafkaTemplate provides asynchronous send methods returning a Future
        ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.send(topic, message);

        // you can register a callback with the listener to receive the result of the send asynchronously
        future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                log.info("sent message='{}' with offset={}", message, result.getRecordMetadata().offset());
            }
            @Override
            public void onFailure(Throwable ex) {
                log.error("unable to send message='{}'", message, ex);
            }
        });
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softsaj.egdvweb.Kafka.Exception.MapperException;
import com.softsaj.egdvweb.Vendas.models.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author Marcos
 */
@Slf4j
@Component
public class KafkaListeners {
   
    private static final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    SimpMessagingTemplate template;

    @KafkaListener(topics = "notification", groupId = "notification-group-id", containerFactory = "kakfaListenerContainerFactory")
    public void listenSenderEmail(String data) {

        Notification notification = fromJson(data, Notification.class);
        log.info("Consumed message: " + data);
        template.convertAndSend("/topic/notif", notification);

    }

    /**
     * Convert json to Object
     * @param json String json value
     * @param clazz Instances of the class
     * @param <T> Object Class
     * @return Object class
     */
    private <T> T fromJson(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new MapperException(e.getMessage());
        }
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Kafka.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softsaj.egdvweb.Kafka.Exception.MapperException;
import com.softsaj.egdvweb.Vendas.models.Notification;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marcos
 */


public class NotificationService{

    private static final ObjectMapper mapper = new ObjectMapper();
  
    private  BrokerProducerService brokerProducerService = new BrokerProducerService();
     private final String bootstrapServers = "localhost:9092";
    private final String topicname = "notification";

   
    

   
    public void send(Notification notification) {
        brokerProducerService.sendNotification(topicname, toJson(notification));
    }


    /**
     * Convert Object to json
     *
     * @param object object
     * @return string json
     */
    private <T> String toJson(T object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new MapperException(e.getMessage());
        }
    }
}

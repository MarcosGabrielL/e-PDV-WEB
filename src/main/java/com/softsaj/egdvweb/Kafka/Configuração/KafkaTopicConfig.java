/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Kafka.Configuração;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.config.TopicBuilder;

/**
 *
 * @author Marcos
 */
@Configuration
public class KafkaTopicConfig {
    
    @Bean
    public NewTopic VendasTopic(){
       return TopicBuilder.name("notification").
               build();
    }
    
}

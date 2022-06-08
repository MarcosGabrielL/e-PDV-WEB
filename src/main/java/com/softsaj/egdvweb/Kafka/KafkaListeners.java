/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author Marcos
 */
@Component
public class KafkaListeners {
    
    @KafkaListener(topics = "vendas", groupId = "groupId")
    void Listener(String data){
        System.out.println("Listener receive: "+data);
    }
    
}

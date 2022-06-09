/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Kafka.Controllers;

import com.softsaj.egdvweb.Kafka.Services.NotificationService;
import com.softsaj.egdvweb.Vendas.models.Notification;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Marcos
 */

@RestController
@RequestMapping("/message")
public class NotificationKafkaController {
    
    
    private NotificationService notificationService = new NotificationService();

    

    @PostMapping("/send")
    public ResponseEntity<Void> sendNotification(@RequestBody Notification notification) {
        notificationService.send(notification);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

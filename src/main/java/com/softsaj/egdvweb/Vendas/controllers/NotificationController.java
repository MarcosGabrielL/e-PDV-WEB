/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.controllers;


import com.softsaj.egdvweb.Vendas.models.Notification;
import com.softsaj.egdvweb.Vendas.services.NotificationService;
import com.softsaj.egdvweb.Vendas.util.TempoDecorrido;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author Marcos
 */
@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/notifications")
public class NotificationController {
    
     @Autowired
    private NotificationService vs;
    
    @GetMapping
    public ResponseEntity<List<Notification>> getAll() {
        List<Notification> movies =  vs.findAll();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
    
    //GEt Notification
     @GetMapping("/notification/{id}")
    public ResponseEntity<Notification> getCienfiloById (@PathVariable("id") Long id
             ,@RequestParam("token") String token) {
        
       
        
        Notification notification = vs.findNotificationById(id);
        return new ResponseEntity<>(notification, HttpStatus.OK);
    }
    
      @GetMapping("/notification/user/{id}")
    public ResponseEntity<List<Notification>> userNotification (@PathVariable("id") Long id
             ,@RequestParam("token") String token) {
        
        List<Notification> movies = vs.userNotification(id.toString());
        List<Notification> ns = new ArrayList();
        for(Notification n : movies){
            n.setDate(TempoDecorrido.TempoDecorrido(n.getDate()));
            ns.add(n);
        }
       
        
         
        return new ResponseEntity<>(ns, HttpStatus.OK);
    }
    
    
    
    @PostMapping("/notification/add")
    public ResponseEntity<Notification> addNotification(@RequestBody Notification movie,@RequestParam("token") String token) {
        
        
        
        Notification newNotification = vs.addNotification(movie);
        URI uri = ServletUriComponentsBuilder.
                fromCurrentRequest().path("/notification/{id}").buildAndExpand(movie.getId()).toUri();
        
        return new ResponseEntity<>(newNotification, HttpStatus.CREATED);
    }
    
    
    @PostMapping("/notification/update/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable("id") Long id, @RequestBody Notification newnotification
            ,@RequestParam("token") String token) {
        
        
        
        Notification notification = vs.findNotificationById(id);
        notification.setIsRead(true);
        //c//inefilo.setTelefone(newnotification.getTelefone());
        //notification.setIdade(newnotification.getIdade());
        //notification.setFoto(newnotification.getFoto());
        Notification updateNotification = vs.updateNotification(notification);//s
        return new ResponseEntity<>(updateNotification, HttpStatus.OK);
    }
    
    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable("id") Long id
            ,@RequestParam("token") String token) {
        
      
        
        vs.deleteNotification(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

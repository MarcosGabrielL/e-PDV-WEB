/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.controllers;


import com.softsaj.egdvweb.Vendas.services.EventoService;
import com.softsaj.egdvweb.Vendas.services.VendidosService;
import java.net.URI;
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
import com.softsaj.egdvweb.Vendas.Relatorios.Evento;

/**
 *
 * @author Marcos
 */
@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/eventos")
public class EventoController {
    
     @Autowired
    private EventoService vs;
    
    @GetMapping
    public ResponseEntity<List<Evento>> getAll() {
        List<Evento> movies =  vs.findAll();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
    
    //GEt Evento
     @GetMapping("/evento/{id}")
    public ResponseEntity<Evento> getCienfiloById (@PathVariable("id") Long id
             ,@RequestParam("token") String token) {
        
       
        
        Evento evento = vs.findEventoById(id);
        return new ResponseEntity<>(evento, HttpStatus.OK);
    }
    
     @GetMapping("/evento/usuario/{email}")
    public ResponseEntity<List<Evento>> findByEmail (@PathVariable("email") String email
             ,@RequestParam("token") String token) {
        
       
        
        List<Evento> movies = vs.findByEmail(email);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
    
    
    @PostMapping("/evento/add")
    public ResponseEntity<Evento> addEvento(@RequestBody Evento movie,@RequestParam("token") String token) {
        
        
        
        Evento newEvento = vs.addEvento(movie);
        URI uri = ServletUriComponentsBuilder.
                fromCurrentRequest().path("/evento/{id}").buildAndExpand(movie.getId()).toUri();
        
        return new ResponseEntity<>(newEvento, HttpStatus.CREATED);
    }
    
    //Update nome,telefone,idade,foto;
    @PutMapping("/evento/update/{id}")
    public ResponseEntity<Evento> updateEvento(@PathVariable("id") Long id, @RequestBody Evento newevento
            ,@RequestParam("token") String token) {
        
        
        
        Evento evento = vs.findEventoById(id);
        //evento.setNome(newevento.getNome());
        //c//inefilo.setTelefone(newevento.getTelefone());
        //evento.setIdade(newevento.getIdade());
        //evento.setFoto(newevento.getFoto());
        Evento updateEvento = vs.updateEvento(evento);//s
        return new ResponseEntity<>(updateEvento, HttpStatus.OK);
    }
    
    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEvento(@PathVariable("id") Long id
            ,@RequestParam("token") String token) {
        
      
        
        vs.deleteEvento(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    
}



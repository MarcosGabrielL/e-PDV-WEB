/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.controllers;

import com.softsaj.egdvweb.models.Dominio;
import com.softsaj.egdvweb.services.DominioService;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
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
import org.springframework.web.bind.annotation.RequestHeader;
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
@RequestMapping("/dominios")
public class DominioController {
    
     @Autowired
    private DominioService vs;
    
    @GetMapping
    public ResponseEntity<List<Dominio>> getAll() {
        List<Dominio> movies =  vs.findAll();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
    
    //GEt Dominio
     @GetMapping("/dominio/{id}")
    public ResponseEntity<Dominio> getCienfiloById (@PathVariable("id") Long id
             ,@RequestHeader("Authorization") String token) {
        
        Dominio dominio = vs.findDominioById(id);
        return new ResponseEntity<>(dominio, HttpStatus.OK);
    }
    
     @GetMapping("/dominio/usuario/{email}")
    public ResponseEntity<List<Dominio>> findByEmail (@PathVariable("email") String email
             ,@RequestHeader("Authorization") String token) {
        
       
        
        List<Dominio> movies = vs.findByEmail(email);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
    
    
    @PostMapping("/dominio/add")
    public ResponseEntity<Dominio> addDominio(@RequestBody Dominio movie, @RequestHeader("Authorization") String token) {
        
        Dominio newDominio = vs.addDominio(movie);
        URI uri = ServletUriComponentsBuilder.
                fromCurrentRequest().path("/dominio/{id}").buildAndExpand(movie.getId()).toUri();
        
        return new ResponseEntity<>(newDominio, HttpStatus.CREATED);
    }
    
    //Update nome,telefone,idade,foto;
    @PutMapping("/dominio/update/{id}")
    public ResponseEntity<Dominio> updateDominio(@PathVariable("id") Long id, @RequestBody Dominio newdominio
            ,@RequestHeader("Authorization") String token) {
        
        
        
        Dominio dominio = vs.findDominioById(id);
        //dominio.setNome(newdominio.getNome());
        //c//inefilo.setTelefone(newdominio.getTelefone());
        //dominio.setIdade(newdominio.getIdade());
        //dominio.setFoto(newdominio.getFoto());
        Dominio updateDominio = vs.updateDominio(dominio);//s
        return new ResponseEntity<>(updateDominio, HttpStatus.OK);
    }
    
    
    
    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDominio(@PathVariable("id") Long id
            ,@RequestHeader("Authorization") String token) {
        
      
        
        vs.deleteDominio(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    
}

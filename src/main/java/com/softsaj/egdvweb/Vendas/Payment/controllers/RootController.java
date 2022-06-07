/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.Payment.controllers;

/**
 * 
 * @author Marcos
 */
import com.softsaj.egdvweb.Vendas.Payment.models.Root;
import com.softsaj.egdvweb.Vendas.Payment.services.RootService;
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
@RequestMapping("/preferences")
public class RootController {
    
     @Autowired
    private RootService vs;
    
    @GetMapping
    public ResponseEntity<List<Root>> getAll() {
        List<Root> movies =  vs.findAll();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
    
    //GEt Root
     @GetMapping("/preference/{id}")
    public ResponseEntity<Root> getCienfiloById (@PathVariable("id") Long id
             ,@RequestParam("token") String token) {
        
       
        
        Root preference = vs.findRootById(id);
        return new ResponseEntity<>(preference, HttpStatus.OK);
    }
    
      @GetMapping("/preference/user/{id}")
    public ResponseEntity<List<Root>> userRoot (@PathVariable("id") Long id
             ,@RequestParam("token") String token) {
        
        List<Root> movies = vs.userRoot(id.toString());
        
       
        
         
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
    
    
    
    @PostMapping("/preference/add")
    public ResponseEntity<Root> addRoot(@RequestBody Root movie,@RequestParam("token") String token) {
        
        
        
        Root newRoot = vs.addRoot(movie);
        URI uri = ServletUriComponentsBuilder.
                fromCurrentRequest().path("/preference/{id}").buildAndExpand(movie.getId()).toUri();
        
        return new ResponseEntity<>(newRoot, HttpStatus.CREATED);
    }
    
    
   
    
    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRoot(@PathVariable("id") Long id
            ,@RequestParam("token") String token) {
        
      
        
        vs.deleteRoot(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
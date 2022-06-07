/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.Payment.controllers;

import com.softsaj.egdvweb.Vendas.Payment.models.ResultPago;
import com.softsaj.egdvweb.Vendas.Payment.services.ResultPagoService;
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
@RequestMapping("/resultpagos")
public class ResultPagoController {
    
     @Autowired
    private ResultPagoService vs;
    
    @GetMapping
    public ResponseEntity<List<ResultPago>> getAll() {
        List<ResultPago> movies =  vs.findAll();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
    
    //GEt ResultPago
     @GetMapping("/resultpago/{id}")
    public ResponseEntity<ResultPago> getCienfiloById (@PathVariable("id") Long id
             ,@RequestParam("token") String token) {
        
       
        
        ResultPago resultpago = vs.findResultPagoById(id);
        return new ResponseEntity<>(resultpago, HttpStatus.OK);
    }
    
      @GetMapping("/resultpago/user/{id}")
    public ResponseEntity<List<ResultPago>> userResultPago (@PathVariable("id") Long id
             ,@RequestParam("token") String token) {
        
        List<ResultPago> movies = vs.userResultPago(id.toString());
        
       
        
         
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
    
    
    
    @PostMapping("/resultpago/add")
    public ResponseEntity<ResultPago> addResultPago(@RequestBody ResultPago movie,@RequestParam("token") String token) {
        
        
        
        ResultPago newResultPago = vs.addResultPago(movie);
        URI uri = ServletUriComponentsBuilder.
                fromCurrentRequest().path("/resultpago/{id}").buildAndExpand(movie.getId()).toUri();
        
        return new ResponseEntity<>(newResultPago, HttpStatus.CREATED);
    }
    
    
   
    
    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteResultPago(@PathVariable("id") Long id
            ,@RequestParam("token") String token) {
        
      
        
        vs.deleteResultPago(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

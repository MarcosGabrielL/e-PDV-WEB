/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.controllers;


import com.softsaj.egdvweb.Vendas.models.Frete;
import com.softsaj.egdvweb.Vendas.services.EventoService;
import com.softsaj.egdvweb.Vendas.services.NotificationService;
import com.softsaj.egdvweb.Vendas.services.FreteService;
import com.softsaj.egdvweb.Vendas.services.VendidosService;
import com.softsaj.egdvweb.Vendas.util.validateToken;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/fretes")
public class FreteController {
    
    
     @Autowired
    private FreteService vs;
   
     @Autowired
     private validateToken validatetoken;
     
     @Autowired
    private VendidosService vds;
     
      @Autowired
    private EventoService es;
      
       @Autowired
    private NotificationService ns;
      
     
    @GetMapping
    public ResponseEntity<List<Frete>> getAll() {
        List<Frete> fretes =  vs.findAll();
        return new ResponseEntity<>(fretes, HttpStatus.OK);
    }
    
    //GEt Frete
     @GetMapping("/frete/{id}")
    public ResponseEntity<Frete> getCienfiloById (@PathVariable("id") Long id
            ,@RequestParam("token") String token) {
        
      //  if(!validatetoken.isLogged(token)){
        //     throw new IllegalStateException("token not valid");
        //}
        
        Frete frete = vs.findFreteById(id);
        return new ResponseEntity<>(frete, HttpStatus.OK);
    }
    
      @GetMapping("/frete/vendedor/{id}")
    public ResponseEntity<Frete> getFreteBbyCinefilo (@PathVariable("id") String vendedorid
            ,@RequestParam("token") String token) {
        
      //  if(!validatetoken.isLogged(token)){
        //     throw new IllegalStateException("token not valid");
        //}
        
        Frete frete = vs.findByVendedor(vendedorid);
        return new ResponseEntity<>(frete, HttpStatus.OK);
    }
    
    @PostMapping("/frete/add")
    public ResponseEntity<Frete> addFrete(
            @RequestBody Frete frete,
            @RequestParam("token") String token) {
        
       // if(!validatetoken.isLogged(token)){
        //     throw new IllegalStateException("token not valid");
       // }
        
       
        Frete newFrete = vs.addFrete(frete);
                
        
        URI uri = ServletUriComponentsBuilder.
                fromCurrentRequest().path("/frete/{id}").buildAndExpand(frete.getId()).toUri();
        
        return new ResponseEntity<>(newFrete, HttpStatus.CREATED);
    }
    
    
     @PostMapping("/frete/att")
    public ResponseEntity<Frete> attFrete(
            @RequestBody Frete newfrete,
            @RequestParam("token") String token) {
        
      
        Frete newFrete = vs.addFrete(newfrete);
                
        URI uri = ServletUriComponentsBuilder.
                fromCurrentRequest().path("/frete/{id}").buildAndExpand(newfrete.getId()).toUri();
        
        return new ResponseEntity<>(newFrete, HttpStatus.CREATED);
    }
    
  
     
}

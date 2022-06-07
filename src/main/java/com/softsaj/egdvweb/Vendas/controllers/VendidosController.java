/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.controllers;

import com.softsaj.egdvweb.Vendas.models.Vendidos;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.softsaj.egdvweb.Vendas.models.Vendidos;
import com.softsaj.egdvweb.Vendas.repositories.VendidosRepository;
import com.softsaj.egdvweb.Vendas.services.VendidosService;
import com.softsaj.egdvweb.Vendas.util.validateToken;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/vendidos")
public class VendidosController {
    
    
     @Autowired
    private VendidosService vs;
    @Autowired
     private validateToken validatetoken;
     
    @GetMapping
    public ResponseEntity<List<Vendidos>> getAll() {
        List<Vendidos> movies =  vs.findAll();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
    
    //GEt Vendidos
     @GetMapping("/vendido/{id}")
    public ResponseEntity<Vendidos> getCienfiloById (@PathVariable("id") Long id
             ,@RequestParam("token") String token) {
        
        if(!validatetoken.isLogged(token)){
             throw new IllegalStateException("token not valid");
        }
        
        Vendidos vendido = vs.findVendidosById(id);
        return new ResponseEntity<>(vendido, HttpStatus.OK);
    }
    
     @GetMapping("/vendidos/vendido/venda/{id}")
    public ResponseEntity<List<Vendidos>> getVendidosByIdVenda (@PathVariable("id") int id
             ,@RequestParam("token") String token) {
        
        //if(!validatetoken.isLogged(token)){
          //   throw new IllegalStateException("token not valid");
        //}
        
        List<Vendidos> vendidos = vs.getVendidosByIdVenda(id);
        return new ResponseEntity<>(vendidos, HttpStatus.OK);
    }
    
    @PostMapping("/vendido/add")
    public ResponseEntity<Vendidos> addVendidos(@RequestBody Vendidos movie,@RequestParam("token") String token) {
        
        if(!validatetoken.isLogged(token)){
             throw new IllegalStateException("token not valid");
        }
        
        Vendidos newVendidos = vs.addVendidos(movie);
        URI uri = ServletUriComponentsBuilder.
                fromCurrentRequest().path("/vendido/{id}").buildAndExpand(movie.getId()).toUri();
        
        return new ResponseEntity<>(newVendidos, HttpStatus.CREATED);
    }
    
    //Update nome,telefone,idade,foto;
    @PutMapping("/vendido/update/{id}")
    public ResponseEntity<Vendidos> updateVendidos(@PathVariable("id") Long id, @RequestBody Vendidos newvendido
            ,@RequestParam("token") String token) {
        
        if(!validatetoken.isLogged(token)){
             throw new IllegalStateException("token not valid");
        }
        
        Vendidos vendido = vs.findVendidosById(id);
        //vendido.setNome(newvendido.getNome());
        //c//inefilo.setTelefone(newvendido.getTelefone());
        //vendido.setIdade(newvendido.getIdade());
        //vendido.setFoto(newvendido.getFoto());
        Vendidos updateVendidos = vs.updateVendidos(vendido);//s
        return new ResponseEntity<>(updateVendidos, HttpStatus.OK);
    }
    
    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteVendidos(@PathVariable("id") Long id
            ,@RequestParam("token") String token) {
        
        if(!validatetoken.isLogged(token)){
             throw new IllegalStateException("token not valid");
        }
        
        vs.deleteVendidos(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}



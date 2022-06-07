/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softsaj.egdvweb.services;

import com.softsaj.egdvweb.exception.UserNotFoundException;
import com.softsaj.egdvweb.models.Vendedor;
import com.softsaj.egdvweb.repositories.VendedorRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
/**
 *
 * @author Marcos
 */
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("http://localhost:4200")
  @Service
public class VendedorService {
    
    @Autowired
    private VendedorRepository rp;
    
     public List<Vendedor> findAll() {
        return rp.findAll();
    }
     
     public Vendedor findVendedorById(Long id) {
        return rp.findVendedorById(id)
                .orElseThrow(() -> new UserNotFoundException("Vendedor by id " + id + " was not found"));
    }
     
     public  List<Vendedor> findByEmail(String email) {
        return rp.findByEmail(email);
    }
     
     public Vendedor addVendedor(Vendedor cinefilo) {
        return rp.save(cinefilo);
    }
     
      public Vendedor updateVendedor(Vendedor cinefilo) {
        return rp.save(cinefilo);
    }
      
      public void deleteVendedor(Long id){
        try{
          rp.deleteVendedorById(id);  
        }catch(DataIntegrityViolationException e){
            throw new DataIntegrityViolationException(
                    "Não foi possivel deletar o Vendedor");
        }
    }
}

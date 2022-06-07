/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.services;

import com.softsaj.egdvweb.Vendas.exception.UserNotFoundException;
import com.softsaj.egdvweb.Vendas.models.Frete;
import com.softsaj.egdvweb.Vendas.repositories.FreteRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marcos
 */
@Service
public class FreteService {
  
    @Autowired
    private FreteRepository rp;
    
     public List<Frete> findAll() {
        return rp.findAll();
    }
     
     public Frete findFreteById(Long id) {
        return rp.findFreteById(id)
                .orElseThrow(() -> new UserNotFoundException("Frete by id " + id + " was not found"));
    }
     
      public Frete findByVendedor(String idvendedor) {
        return rp.findByVendedor( idvendedor);
    }
      
     
     public Frete addFrete(Frete cinefilo) {
        return rp.save(cinefilo);
    }
     
      public Frete updateFrete(Frete cinefilo) {
        return rp.save(cinefilo);
    }
      
      public void deleteFrete(Long id){
        try{
          rp.deleteFreteById(id);  
        }catch(DataIntegrityViolationException e){
            throw new DataIntegrityViolationException(
                    "Não foi possivel deletar o Frete");
        }
    }
    
}

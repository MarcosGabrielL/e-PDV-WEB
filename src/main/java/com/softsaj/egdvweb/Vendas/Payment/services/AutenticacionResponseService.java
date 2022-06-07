/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.Payment.services;

import com.softsaj.egdvweb.Vendas.Payment.models.AutenticacionResponse;
import com.softsaj.egdvweb.Vendas.Payment.repositories.AutenticacionResponseRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marcos
 */

@Service
public class AutenticacionResponseService {
    
 @Autowired
    private AutenticacionResponseRepository rp;
    
     public List<AutenticacionResponse> findAll() {
        return rp.findAll();
    }
     
     public AutenticacionResponse findAutenticacionResponseById(Long id) {
        return rp.findAutenticacionResponseById(id);
    }
    
     public List<AutenticacionResponse> userAutenticacionResponse(String id) {
        return rp.userAutenticacionResponse(id);
    }
    
     
     public AutenticacionResponse addAutenticacionResponse(AutenticacionResponse cinefilo) {
        return rp.save(cinefilo);
    }
     
      public AutenticacionResponse updateAutenticacionResponse(AutenticacionResponse cinefilo) {
        return rp.save(cinefilo);
    }
      
      public void deleteAutenticacionResponse(Long id){
        try{
          rp.deleteAutenticacionResponseById(id);  
        }catch(DataIntegrityViolationException e){
            throw new DataIntegrityViolationException(
                    "Não foi possivel deletar o AutenticacionResponse");
        }
    }
      
}

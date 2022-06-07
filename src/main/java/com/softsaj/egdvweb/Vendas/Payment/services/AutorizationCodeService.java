/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.Payment.services;

import com.softsaj.egdvweb.Vendas.Payment.models.AutorizationCode;
import com.softsaj.egdvweb.Vendas.Payment.repositories.AutorizationCodeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marcos
 */
@Service
public class AutorizationCodeService {
    
 @Autowired
    private AutorizationCodeRepository rp;
    
     public List<AutorizationCode> findAll() {
        return rp.findAll();
    }
     
     public AutorizationCode findAutorizationCodeById(Long id) {
        return rp.findAutorizationCodeById(id);
    }
    
     public List<AutorizationCode> userAutorizationCode(String id) {
        return rp.userAutorizationCode(id);
    }
    
     
     public AutorizationCode addAutorizationCode(AutorizationCode cinefilo) {
        return rp.save(cinefilo);
    }
     
      public AutorizationCode updateAutorizationCode(AutorizationCode cinefilo) {
        return rp.save(cinefilo);
    }
      
      public void deleteAutorizationCode(Long id){
        try{
          rp.deleteAutorizationCodeById(id);  
        }catch(DataIntegrityViolationException e){
            throw new DataIntegrityViolationException(
                    "Não foi possivel deletar o AutorizationCode");
        }
    }
      
}


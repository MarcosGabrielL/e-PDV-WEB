/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.Payment.services;

/**
 *
 * @author Marcos
 */
import com.softsaj.egdvweb.Vendas.Payment.models.Root;
import com.softsaj.egdvweb.Vendas.Payment.repositories.RootRepository;
import java.util.List;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
/**
 *
 * @author Marcos
 */
@Service
public class RootService {
 @Autowired
    private RootRepository rp;
    
     public List<Root> findAll() {
        return rp.findAll();
    }
     
     public Root findRootById(Long id) {
        return rp.findRootById(id);
    }
    
     public List<Root> userRoot(String id) {
        return rp.userRoot(id);
    }
    
     
     public Root addRoot(Root cinefilo) {
        return rp.save(cinefilo);
    }
     
      public Root updateRoot(Root cinefilo) {
        return rp.save(cinefilo);
    }
      
      public void deleteRoot(Long id){
        try{
          rp.deleteRootById(id);  
        }catch(DataIntegrityViolationException e){
            throw new DataIntegrityViolationException(
                    "Não foi possivel deletar o Root");
        }
    }
      
}


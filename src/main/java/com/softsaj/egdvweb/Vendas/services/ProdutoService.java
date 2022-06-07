/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.services;

import com.softsaj.egdvweb.Vendas.exception.UserNotFoundException;
import com.softsaj.egdvweb.Vendas.models.Produto;
import com.softsaj.egdvweb.Vendas.repositories.ProdutoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marcos
 */
 @Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository rp;
    
     public List<Produto> findAll() {
        return rp.findAll();
    }
     
     public Produto findProdutoById(Long id) {
        return rp.findProdutoById(id)
                .orElseThrow(() -> new UserNotFoundException("Produto by id " + id + " was not found"));
    }
     
     public List<Produto> findProdutoByIdVendedor(String id) {
        return rp.findProdutoByIdVendedor(id)
                .orElseThrow(() -> new UserNotFoundException("Produto by id " + id + " was not found"));
    }
     
     public Produto addProduto(Produto cinefilo) {
        return rp.save(cinefilo);
    }
     
      public Produto updateProduto(Produto cinefilo) {
        return rp.save(cinefilo);
    }
      
      public void deleteProduto(Long id){
        try{
          rp.deleteProdutoById(id);  
        }catch(DataIntegrityViolationException e){
            throw new DataIntegrityViolationException(
                    "Não foi possivel deletar o Produto");
        }
    }
    
}

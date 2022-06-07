/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.repositories;

import com.softsaj.egdvweb.Vendas.models.Frete;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Marcos
 */
@Repository
public interface FreteRepository extends JpaRepository<Frete, Long> {
    
     Optional<Frete> findFreteById(Long id);
     
    
      @Query("SELECT u FROM Frete u WHERE u.vendedorid =?1")
     Frete findByVendedor(String idvendedor);
     
     void deleteFreteById(Long id);
}

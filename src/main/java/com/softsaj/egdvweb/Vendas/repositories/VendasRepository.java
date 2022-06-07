/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softsaj.egdvweb.Vendas.repositories;

import com.softsaj.egdvweb.Vendas.models.*;
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
public interface VendasRepository extends JpaRepository<Vendas, Long> {
    
     Optional<Vendas> findVendasById(Long id);
     
      @Query("SELECT u FROM Vendas u WHERE u.diavenda = ?1 and u.vendedor_id = ?2 order by u.datavenda")
     List<Vendas> findByData(String datavenda, String idvendedor);
     
      @Query("SELECT u FROM Vendas u WHERE u.diavenda between ?1 and ?2 and u.vendedor_id = ?2")
     List<Vendas> findAllByMes(String datainicio, String datafinal, String idvendedor);
     
     
      @Query("SELECT u FROM Vendas u WHERE u.comprador_id = ?1")
     List<Vendas> findVendasByComprador(String idcomprador);
     
    
     
     void deleteVendasById(Long id);
}
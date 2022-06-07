/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.repositories;


import com.softsaj.egdvweb.models.PerfilPagamento;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.softsaj.egdvweb.models.PerfilPagamento;
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
public interface PerfilPagamentoRepository extends JpaRepository<PerfilPagamento, Long> {
    
     Optional<PerfilPagamento> findPerfilPagamentoById(Long id);
     
     @Query("select n from PerfilPagamento n where n.email = ?1")
	 Optional<PerfilPagamento> userPerfilPagamento(String userId);
     
     void deletePerfilPagamentoById(Long id);
}

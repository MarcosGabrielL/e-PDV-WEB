/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.repositories;


import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.softsaj.egdvweb.Vendas.Relatorios.Evento;

/**
 *
 * @author Marcos
 */
@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    
      @Query("SELECT u FROM Evento u WHERE u.usuario = ?1 order by u.date")
      List<Evento> findByEmail(String usuario);
    
     Optional<Evento> findEventoById(Long id);
     
     void deleteEventoById(Long id);

}

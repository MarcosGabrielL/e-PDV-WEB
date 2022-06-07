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
public interface VendidosRepository extends JpaRepository<Vendidos, Long> {
    
     Optional<Vendidos> findVendidosById(Long id);
     
     @Query("SELECT u FROM Vendidos u WHERE u.IdVenda = ?1")
     Optional<List<Vendidos>> getVendidosByIdVenda(int id);
     
     void deleteVendidosById(Long id);
}
package com.softsaj.egdvweb.repositories;

import com.softsaj.egdvweb.models.Vendedor;
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
public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
    
     Optional<Vendedor> findVendedorById(Long id);
     
     void deleteVendedorById(Long id);
     
     @Query("SELECT u FROM Vendedor u WHERE u.email = ?1")
      List<Vendedor> findByEmail(String usuario);
      
      @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM Vendedor s WHERE s.Id = ?1")
    Boolean isVendedorExitsById(Long id);
}
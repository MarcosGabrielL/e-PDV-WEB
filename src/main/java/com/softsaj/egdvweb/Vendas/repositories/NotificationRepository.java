/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
public interface NotificationRepository extends JpaRepository<Notification,Integer> {

	Notification findNotificationById(Long id);

	@Query("select n from Notification n where n.usuario = ?1 and n.isRead = false ORDER BY n.date DESC")
	List<Notification> userNotification(String userId);

	 void deleteNotificationById(Long id);
	
}

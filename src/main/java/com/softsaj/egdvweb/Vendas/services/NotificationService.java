
package com.softsaj.egdvweb.Vendas.services;

import com.softsaj.egdvweb.Vendas.exception.UserNotFoundException;
import com.softsaj.egdvweb.Vendas.models.Notification;
import com.softsaj.egdvweb.Vendas.models.Vendas;
import com.softsaj.egdvweb.Vendas.repositories.NotificationRepository;
import com.softsaj.egdvweb.Vendas.repositories.VendasRepository;
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
public class NotificationService {
 @Autowired
    private NotificationRepository rp;
    
     public List<Notification> findAll() {
        return rp.findAll();
    }
     
     public Notification findNotificationById(Long id) {
        return rp.findNotificationById(id);
    }
    
     public List<Notification> userNotification(String id) {
        return rp.userNotification(id);
    }
    
     
     public Notification addNotification(Notification cinefilo) {
        return rp.save(cinefilo);
    }
     
      public Notification updateNotification(Notification cinefilo) {
        return rp.save(cinefilo);
    }
      
      public void deleteNotification(Long id){
        try{
          rp.deleteNotificationById(id);  
        }catch(DataIntegrityViolationException e){
            throw new DataIntegrityViolationException(
                    "Não foi possivel deletar o Notification");
        }
    }

}

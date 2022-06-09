/*
 * Copyright (C) Marcos Gabriel L from 2019 to Present
 * All rights Reserved
 */
package com.softsaj.egdvweb.repositories;

import com.softsaj.egdvweb.security.User;
import com.softsaj.egdvweb.security.UserRepository;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
 
 
/**
 *
 * @author Marcos
 */
@SpringBootTest
public class UserRepoTest {
    
     @Autowired
    private UserRepository userRepo;
    
    @Test
    void isvendedorExitsById() {
       
                
                
        User user = new User();
        user.setId(new Long(1));
        user.setEmail("email");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPassword("password");
        user.setTipo("1");
        user.setVerify(true);
        
        userRepo.save(user);
        
        Boolean actualResult = userRepo.isUserExitsById(user.getId());
        
        assertThat(actualResult).isTrue();
    }
}

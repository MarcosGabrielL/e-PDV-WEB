/*
 * Copyright (C) Marcos Gabriel L from 2019 to Present
 * All rights Reserved
 */
package com.softsaj.egdvweb.controller;

import com.softsaj.egdvweb.Abstract.AbstractTest;
import com.softsaj.egdvweb.security.JwtUtil;
import com.softsaj.egdvweb.security.User;
import javax.ws.rs.core.Application;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;


/**
 *
 * @author Marcos
 */

public class AppControllerTest extends AbstractTest {
    
    @Autowired
    private JwtUtil jwtUtil;
    
   @Override
   @Before
   public void setUp() {
       
      super.setUp();
   }
   
   
   
   @Test
   public void getusersList() throws Exception {
      String uri = "/users";
      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
      
      int status = mvcResult.getResponse().getStatus();
      assertEquals(200, status);
      String content = mvcResult.getResponse().getContentAsString();
      User[] productlist = super.mapFromJson(content, User[].class);
      assertTrue(productlist.length > 0);
   }
   
   @Test
   public void createuser() throws Exception {
      String uri = "/process_register";
      User product = new User();
      product.setId(new Long(1));
        product.setEmail("email");
        product.setFirstName("firstName");
        product.setLastName("lastName");
        product.setPassword("password");
        product.setTipo("1");
        product.setVerify(false);
      String inputJson = super.mapToJson(product);
      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
         .contentType(MediaType.APPLICATION_JSON_VALUE)
         .content(inputJson)).andReturn();
      
      int status = mvcResult.getResponse().getStatus();
      assertEquals(201, status);
      String content = mvcResult.getResponse().getContentAsString();
      assertEquals(inputJson, content);
   }
   
   @Test
   public void authenticate() throws Exception {
      String uri = "/authenticate";
      User product = new User();
      product.setId(new Long(1));
        product.setEmail("email");
        product.setFirstName("firstName");
        product.setLastName("lastName");
        product.setPassword("password");
        product.setTipo("1");
        product.setVerify(false);
      String inputJson = super.mapToJson(product);
      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
         .contentType(MediaType.APPLICATION_JSON_VALUE)
         .content(inputJson)).andReturn();
      
      int status = mvcResult.getResponse().getStatus();
      assertEquals(200, status);
      String content = mvcResult.getResponse().getContentAsString();
      assertTrue(jwtUtil.IsValidToken(content,product.getEmail() ));
   }
   //@Test
   public void deleteuser() throws Exception {
      String uri = "/products/2";
      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
      int status = mvcResult.getResponse().getStatus();
      assertEquals(200, status);
      String content = mvcResult.getResponse().getContentAsString();
      assertEquals(content, "user is deleted successsfully");
   }
}

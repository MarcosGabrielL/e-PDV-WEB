/*
 * Copyright (C) Marcos Gabriel L from 2019 to Present
 * All rights Reserved
 */
package com.softsaj.egdvweb.controller;

import com.softsaj.egdvweb.Abstract.AbstractTest;
import com.softsaj.egdvweb.security.User;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


/**
 *
 * @author Marcos
 */
public class AppControllerTest extends AbstractTest {
    
   @Override
   @Before
   public void setUp() {
      super.setUp();
   }
   
   @Test
   public void getusersList() throws Exception {
      String uri = "/products";
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
      String uri = "/products";
      User product = new User();
      product.setId(new Long(1));
        product.setEmail("email");
        product.setFirstName("firstName");
        product.setLastName("lastName");
        product.setPassword("password");
        product.setTipo("1");
        product.setVerify(true);
      String inputJson = super.mapToJson(product);
      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
         .contentType(MediaType.APPLICATION_JSON_VALUE)
         .content(inputJson)).andReturn();
      
      int status = mvcResult.getResponse().getStatus();
      assertEquals(201, status);
      String content = mvcResult.getResponse().getContentAsString();
      assertEquals(content, "user is created successfully");
   }
   @Test
   public void updateuser() throws Exception {
      String uri = "/products/2";
      User product = new User();
      product.setId(new Long(1));
        product.setEmail("email");
        product.setFirstName("firstName");
        product.setLastName("lastName");
        product.setPassword("password");
        product.setTipo("1");
        product.setVerify(true);
      String inputJson = super.mapToJson(product);
      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
         .contentType(MediaType.APPLICATION_JSON_VALUE)
         .content(inputJson)).andReturn();
      
      int status = mvcResult.getResponse().getStatus();
      assertEquals(200, status);
      String content = mvcResult.getResponse().getContentAsString();
      assertEquals(content, "user is updated successsfully");
   }
   @Test
   public void deleteuser() throws Exception {
      String uri = "/products/2";
      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
      int status = mvcResult.getResponse().getStatus();
      assertEquals(200, status);
      String content = mvcResult.getResponse().getContentAsString();
      assertEquals(content, "user is deleted successsfully");
   }
}

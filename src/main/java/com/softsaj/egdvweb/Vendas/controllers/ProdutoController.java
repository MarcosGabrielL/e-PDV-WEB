/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Marcos
 */

import com.softsaj.egdvweb.Vendas.file.FileDB;
import com.softsaj.egdvweb.Vendas.file.FileStorageService;
import com.softsaj.egdvweb.Vendas.models.Produto;
import com.softsaj.egdvweb.Vendas.models.ProdutoDTO;
import com.softsaj.egdvweb.Vendas.repositories.ProdutoRepository;
import com.softsaj.egdvweb.Vendas.services.ProdutoService;
import com.softsaj.egdvweb.Vendas.util.validateToken;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    
    
     @Autowired
    private ProdutoService vs;
     
     @Autowired
     private validateToken validatetoken;
     
      @Autowired
  private FileStorageService storageService;
     
    @GetMapping
    public ResponseEntity<List<Produto>> getAll() {
        List<Produto> produtos =  vs.findAll();
        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }
    
    //GEt Produto
    @GetMapping(path = "/produto")
    public ResponseEntity<Produto> getCienfiloById (
            @RequestParam("token") String token,
            @RequestParam("id") Long id) {
        
        
        
       // if(!validatetoken.isLogged(token)){
         //    throw new IllegalStateException("token not valid");
       // }
        
        Produto produto = vs.findProdutoById(id);
        return new ResponseEntity<>(produto, HttpStatus.OK);
    }

	@GetMapping("/produto/byvendedor")
    public ResponseEntity<List<Produto>> getProdutoByIdVendedor (
            @RequestParam("token") String token,
            @RequestParam("id") String id) {
        
        
        
       // if(!validatetoken.isLogged(token)){
         //    throw new IllegalStateException("token not valid");
       // }
        
        List<Produto> produtos = vs.findProdutoByIdVendedor(id);
        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }
    
    @GetMapping("/produtodto/byvendedor")
    public ResponseEntity<List<ProdutoDTO>> getProdutoDAOByIdVendedor (
            @RequestParam("token") String token,
            @RequestParam("id") String id) {
        
        
        
       // if(!validatetoken.isLogged(token)){
         //    throw new IllegalStateException("token not valid");
       // }
       
        List<Produto> produtos = vs.findProdutoByIdVendedor(id);
        
        List<ProdutoDTO> produtosdao = new ArrayList();
        
        for(Produto p: produtos){
            ProdutoDTO pdao = new ProdutoDTO();
            pdao.setId(p.getId());
            pdao.setCodigo(p.getCodigo());
            pdao.setDescricao(p.getDescricao());
            pdao.setPrecoun(p.getPrecoun());
            pdao.setQuantidade(p.getQuantidade());
            pdao.setTipo(p.getTipo());
            pdao.setUnidade(p.getUnidade());
            pdao.setData(p.getData());
            pdao.setVendedor_id(p.getVendedor_id());
            
            List<FileDB> files = storageService.findByIdProduto(p.getId().toString());
            pdao.setFiles(files);
            
             System.out.println("Pdao Files"+pdao.getFiles());
            for(FileDB d: files){
            System.out.println("Files: "+ d.getName());
                    }
            
            List<String> urls = new ArrayList();
            for(FileDB f: files){
                urls.add("data:image/png;base64,"+Arrays.toString(f.getData()));
            }
            pdao.setUrls(urls);
            
             System.out.println("Files"+pdao.getUrls());
            
            produtosdao.add(pdao);
        }
        
        
        return new ResponseEntity<>(produtosdao, HttpStatus.OK);
    }
    
    
    @PostMapping("/produto/add")
    public ResponseEntity<Produto> addProduto(@RequestBody Produto produto
    ,@RequestParam("token") String token,
    @RequestParam("vendedorid") String id) {
        
       // if(!validatetoken.isLogged(token)){
        //     throw new IllegalStateException("token not valid");
      //  }
      
      System.out.println("ID Vendedor: "+id);
       produto.setVendedor_id(id);
        Produto newProduto = vs.addProduto(produto);
        URI uri = ServletUriComponentsBuilder.
                fromCurrentRequest().path("/produto/{id}").buildAndExpand(produto.getId()).toUri();
        
        return new ResponseEntity<>(newProduto, HttpStatus.CREATED);
    }
    
    //Update nome,telefone,idade,foto;
    @PutMapping("/produto/update/{id}")
    public ResponseEntity<Produto> updateProduto(@PathVariable("id") Long id, 
            @RequestBody Produto newproduto, 
            @RequestParam("token") String token) {
        
        if(!validatetoken.isLogged(token)){
             throw new IllegalStateException("token not valid");
        }
        
        
        Produto produto = vs.findProdutoById(id);
        produto.setDescricao(newproduto.getDescricao());
        produto.setQuantidade(newproduto.getQuantidade());
        produto.setPrecoun(newproduto.getPrecoun());
        produto.setUnidade(newproduto.getUnidade());
        //produto.setFoto(newproduto.getFoto);
        
        Produto updateProduto = vs.updateProduto(produto);//s
        return new ResponseEntity<>(updateProduto, HttpStatus.OK);
    }
    
    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduto(@PathVariable("id") Long id,
            @RequestParam("token") String token) {
        
        if(!validatetoken.isLogged(token)){
             throw new IllegalStateException("token not valid");
        }
        
        vs.deleteProduto(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}


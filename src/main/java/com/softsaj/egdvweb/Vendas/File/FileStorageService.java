/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softsaj.egdvweb.Vendas.File;

/**
 *
 * @author Marcos
 */

import com.softsaj.egdvweb.Vendas.models.Produto;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileStorageService {

  @Autowired
  private FileDBRepository fileDBRepository;

  public FileDB store(MultipartFile file, String idpost, String idvendedor) throws IOException {
      
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    FileDB FileDB = new FileDB();
    FileDB.setData(file.getBytes());
    FileDB.setIdpost(idpost);
    FileDB.setIdvendedor(idvendedor);
    FileDB.setName(fileName);
    FileDB.setType(file.getContentType());

    return fileDBRepository.save(FileDB);
  }
  
  
   public FileDB storeupdate(MultipartFile file, String idpost, String idvendedor, Long id) throws IOException {
      
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    FileDB FileDB = new FileDB();
    FileDB.setId(id);
    FileDB.setData(file.getBytes());
    FileDB.setIdpost(idpost);
    FileDB.setIdvendedor(idvendedor);
    FileDB.setName(fileName);
    FileDB.setType(file.getContentType());

    return fileDBRepository.save(FileDB);
  }

  public FileDB getFile(Long id) {
    return fileDBRepository.findById(id).get();
  }
  
  public List<FileDB> findByIdProduto(String id) {
    return fileDBRepository.findByIdProduto(id);
  }
  
  public List<FileDB> findByIdVendedor(String id) {
    return fileDBRepository.findByIdVendedor(id);
  }
  
  public Stream<FileDB> getAllFiles() {
    return fileDBRepository.findAll().stream();
  }
}

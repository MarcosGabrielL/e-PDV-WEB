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
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

  
@Controller
@CrossOrigin("http://localhost:4200")
public class FileController {

  @Autowired
  private FileStorageService storageService;

  @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file,
          @RequestParam("idproduct") String idproduct) {
    String message = "";
    try {
      storageService.store(file, idproduct, null);

      message = "Uploaded the file successfully: " + file.getOriginalFilename();
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e) {
      message = "Could not upload the file: " + file.getOriginalFilename() + "! "+e.getLocalizedMessage();
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
  }
  
  @PostMapping(value = "/loja/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ResponseMessage> SaveFileLoja(@RequestParam("file") MultipartFile file,
          @RequestParam("idproduct") String idvendedor,
          @RequestParam("id") Long id){
    String message = "";
    try {
      storageService.storeupdate(file, "", idvendedor, id);

      message = "Uploaded the file successfully: " + file.getOriginalFilename();
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e) {
      message = "Could not upload the file: " + file.getOriginalFilename() + "! "+e.getLocalizedMessage();
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
  }
  
  @PostMapping(value = "/loja/saveFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ResponseMessage> uploadFileLoja(@RequestParam("file") MultipartFile file,
          @RequestParam("idproduct") String idvendedor) {
    String message = "";
    try {
      storageService.store(file, "", idvendedor);

      message = "Uploaded the file successfully: " + file.getOriginalFilename();
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e) {
      message = "Could not upload the file: " + file.getOriginalFilename() + "! "+e.getLocalizedMessage();
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
  }

  @GetMapping("/files")
  public ResponseEntity<List<ResponseFile>> getListFiles() {
    List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
      String fileDownloadUri = ServletUriComponentsBuilder
          .fromCurrentContextPath()
          .path("/files/")
          .path(dbFile.getId().toString())
          .toUriString();

      return new ResponseFile(
          dbFile.getName(),
          fileDownloadUri,
          dbFile.getType(),
          dbFile.getData().length);
    }).collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(files);
  }
  
  @GetMapping("/filelist/produto/{id}")
  public ResponseEntity<List<FileDB>> findByIdProduto(@PathVariable String id) {
     List<FileDB> files = storageService.findByIdProduto(id);
      
     
    return ResponseEntity.status(HttpStatus.OK).body(files);
  }
  
  @GetMapping("/filelist/loja/{id}")
  public ResponseEntity<List<FileDB>> findByIdLoja(@PathVariable String id) {
     List<FileDB> files = storageService.findByIdVendedor(id);
      
     
    return ResponseEntity.status(HttpStatus.OK).body(files);
  }
  

  @GetMapping("/download/file/{id}")
  public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
    FileDB fileDB = storageService.getFile(id);

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
        .body(fileDB.getData());
  }
  
  @GetMapping("/file/{id}")
  public ResponseEntity<byte[]> showimage(@PathVariable Long id) {
    FileDB fileDB = storageService.getFile(id);

    return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_PNG).body(fileDB.getData());
  }
  
  
}
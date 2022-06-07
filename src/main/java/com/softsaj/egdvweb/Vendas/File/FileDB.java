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
import javax.persistence.*;


@Entity
@Table(name = "files")
public class FileDB {
    
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  private String name;
  
   private String idpost;
   
   @Column(nullable = true, unique = false, length = 30)
   private String idvendedor;

  private String type;

  @Lob
  private byte[] data;

  public FileDB() {
  }

    public FileDB(Long id, String name, String idpost, String idvendedor, String type, byte[] data) {
        this.id = id;
        this.name = name;
        this.idpost = idpost;
        this.idvendedor = idvendedor;
        this.type = type;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdpost() {
        return idpost;
    }

    public void setIdpost(String idpost) {
        this.idpost = idpost;
    }

    public String getIdvendedor() {
        return idvendedor;
    }

    public void setIdvendedor(String idvendedor) {
        this.idvendedor = idvendedor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
    
    

}

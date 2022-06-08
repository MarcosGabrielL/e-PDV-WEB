/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.models;

import com.softsaj.egdvweb.Vendas.File.FileDB;
import java.io.File;
import java.util.List;

/**
 *
 * @author Marcos
 */
public class ProdutoDTO {
    
    
    private Long id;
    private String codigo;
    private String descricao;
    private String precoun;
    private String Ventrada;
    private float quantidade;
    private String tipo;
    private String Unidade;
    private String UnidadeTributavel;
    private String data;
    private String loja = "Sede";
    private float SubTotal;
    private String vendedor_id;
    private List<FileDB> files;
    private List<String> urls;

    public ProdutoDTO(Long id, String codigo, String descricao, String precoun, String Ventrada, float quantidade, String tipo, String Unidade, String UnidadeTributavel, String data, float SubTotal, String vendedor_id, List<FileDB> files, List<String> urls) {
        this.id = id;
        this.codigo = codigo;
        this.descricao = descricao;
        this.precoun = precoun;
        this.Ventrada = Ventrada;
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.Unidade = Unidade;
        this.UnidadeTributavel = UnidadeTributavel;
        this.data = data;
        this.SubTotal = SubTotal;
        this.vendedor_id = vendedor_id;
        this.files = files;
        this.urls = urls;
    }

    public ProdutoDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPrecoun() {
        return precoun;
    }

    public void setPrecoun(String precoun) {
        this.precoun = precoun;
    }

    public String getVentrada() {
        return Ventrada;
    }

    public void setVentrada(String Ventrada) {
        this.Ventrada = Ventrada;
    }

    public float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(float quantidade) {
        this.quantidade = quantidade;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUnidade() {
        return Unidade;
    }

    public void setUnidade(String Unidade) {
        this.Unidade = Unidade;
    }

    public String getUnidadeTributavel() {
        return UnidadeTributavel;
    }

    public void setUnidadeTributavel(String UnidadeTributavel) {
        this.UnidadeTributavel = UnidadeTributavel;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getLoja() {
        return loja;
    }

    public void setLoja(String loja) {
        this.loja = loja;
    }

    public float getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(float SubTotal) {
        this.SubTotal = SubTotal;
    }

    public String getVendedor_id() {
        return vendedor_id;
    }

    public void setVendedor_id(String vendedor_id) {
        this.vendedor_id = vendedor_id;
    }

    public List<FileDB> getFiles() {
        return files;
    }

    public void setFiles(List<FileDB> files) {
        this.files = files;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
    
    
    
}

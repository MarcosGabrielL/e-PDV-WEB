/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Marcos
 */
@Entity
@Table(name = "vendedores")
public class Vendedor {
    
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     
    
     private String CNPJ;
     @Column(nullable = false, unique = false, length = 45)
        private String nomefantasia; 
     @Column(nullable = false, unique = false, length = 250)
           private String descricao;   
        private String Cidade;
        private String CEP;
        private String CFOP;
     private String razaosocial;
     @Column(nullable = false, unique = false, length = 45)
    private String rua;
    private String Bairro;
    private String Numero;
    private String Estado;
    private String Uf;
    @Column(nullable = false, unique = false, length = 45)
    private String telefone;
      @Column(nullable = false, unique = true, length = 45)
    private String email;
    private String Certificado;
    private String  NumeroSérie;
    /*
    0 - nenhum
    1 - Free
    2 - Medio
    3 - Top*/
     @Column(nullable = false, unique = false, length = 4)
    private int  ambiente;
     /*
     0- nada
    1 - Teste
    2 - Vencido
    3 - Pago*/
     
    @Column(nullable = false, unique = false, length = 4)
    private int serie;
    
    @Column(nullable = false, unique = false, length = 35)
     private String datainicio;
    
    @Column(nullable = false, unique = false, length = 35)
     private String datafim;
    private String CódigoRegimeTributario;
    private String ICMS;
    private String PIS;
    private String COFINS;
    private String IPI;
       private int CTipo;
        private String CSenha;
        private String CValidade;
        private String NCM;
        private String CST;
            private String CodigoCity;
            private String InscricaoEstadual;
            private String IBPT;

            private String IdToken;
            private String CSC;

    public Vendedor() {
        super();
    }

    public Vendedor(Long id, String CNPJ, String nomefantasia, String descricao, String Cidade, String CEP, String CFOP, String razaosocial, String rua, String Bairro, String Numero, String Estado, String Uf, String telefone, String email, String Certificado, String NumeroSérie, int ambiente, int serie, String datainicio, String datafim, String CódigoRegimeTributario, String ICMS, String PIS, String COFINS, String IPI, int CTipo, String CSenha, String CValidade, String NCM, String CST, String CodigoCity, String InscricaoEstadual, String IBPT, String IdToken, String CSC) {
        this.id = id;
        this.CNPJ = CNPJ;
        this.nomefantasia = nomefantasia;
        this.descricao = descricao;
        this.Cidade = Cidade;
        this.CEP = CEP;
        this.CFOP = CFOP;
        this.razaosocial = razaosocial;
        this.rua = rua;
        this.Bairro = Bairro;
        this.Numero = Numero;
        this.Estado = Estado;
        this.Uf = Uf;
        this.telefone = telefone;
        this.email = email;
        this.Certificado = Certificado;
        this.NumeroSérie = NumeroSérie;
        this.ambiente = ambiente;
        this.serie = serie;
        this.datainicio = datainicio;
        this.datafim = datafim;
        this.CódigoRegimeTributario = CódigoRegimeTributario;
        this.ICMS = ICMS;
        this.PIS = PIS;
        this.COFINS = COFINS;
        this.IPI = IPI;
        this.CTipo = CTipo;
        this.CSenha = CSenha;
        this.CValidade = CValidade;
        this.NCM = NCM;
        this.CST = CST;
        this.CodigoCity = CodigoCity;
        this.InscricaoEstadual = InscricaoEstadual;
        this.IBPT = IBPT;
        this.IdToken = IdToken;
        this.CSC = CSC;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getNomefantasia() {
        return nomefantasia;
    }

    public void setNomefantasia(String nomefantasia) {
        this.nomefantasia = nomefantasia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCidade() {
        return Cidade;
    }

    public void setCidade(String Cidade) {
        this.Cidade = Cidade;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public String getCFOP() {
        return CFOP;
    }

    public void setCFOP(String CFOP) {
        this.CFOP = CFOP;
    }

    public String getRazaosocial() {
        return razaosocial;
    }

    public void setRazaosocial(String razaosocial) {
        this.razaosocial = razaosocial;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return Bairro;
    }

    public void setBairro(String Bairro) {
        this.Bairro = Bairro;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String Numero) {
        this.Numero = Numero;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public String getUf() {
        return Uf;
    }

    public void setUf(String Uf) {
        this.Uf = Uf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCertificado() {
        return Certificado;
    }

    public void setCertificado(String Certificado) {
        this.Certificado = Certificado;
    }

    public String getNumeroSérie() {
        return NumeroSérie;
    }

    public void setNumeroSérie(String NumeroSérie) {
        this.NumeroSérie = NumeroSérie;
    }

    public int getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(int ambiente) {
        this.ambiente = ambiente;
    }

    public int getSerie() {
        return serie;
    }

    public void setSerie(int serie) {
        this.serie = serie;
    }

    public String getDatainicio() {
        return datainicio;
    }

    public void setDatainicio(String datainicio) {
        this.datainicio = datainicio;
    }

    public String getDatafim() {
        return datafim;
    }

    public void setDatafim(String datafim) {
        this.datafim = datafim;
    }

    public String getCódigoRegimeTributario() {
        return CódigoRegimeTributario;
    }

    public void setCódigoRegimeTributario(String CódigoRegimeTributario) {
        this.CódigoRegimeTributario = CódigoRegimeTributario;
    }

    public String getICMS() {
        return ICMS;
    }

    public void setICMS(String ICMS) {
        this.ICMS = ICMS;
    }

    public String getPIS() {
        return PIS;
    }

    public void setPIS(String PIS) {
        this.PIS = PIS;
    }

    public String getCOFINS() {
        return COFINS;
    }

    public void setCOFINS(String COFINS) {
        this.COFINS = COFINS;
    }

    public String getIPI() {
        return IPI;
    }

    public void setIPI(String IPI) {
        this.IPI = IPI;
    }

    public int getCTipo() {
        return CTipo;
    }

    public void setCTipo(int CTipo) {
        this.CTipo = CTipo;
    }

    public String getCSenha() {
        return CSenha;
    }

    public void setCSenha(String CSenha) {
        this.CSenha = CSenha;
    }

    public String getCValidade() {
        return CValidade;
    }

    public void setCValidade(String CValidade) {
        this.CValidade = CValidade;
    }

    public String getNCM() {
        return NCM;
    }

    public void setNCM(String NCM) {
        this.NCM = NCM;
    }

    public String getCST() {
        return CST;
    }

    public void setCST(String CST) {
        this.CST = CST;
    }

    public String getCodigoCity() {
        return CodigoCity;
    }

    public void setCodigoCity(String CodigoCity) {
        this.CodigoCity = CodigoCity;
    }

    public String getInscricaoEstadual() {
        return InscricaoEstadual;
    }

    public void setInscricaoEstadual(String InscricaoEstadual) {
        this.InscricaoEstadual = InscricaoEstadual;
    }

    public String getIBPT() {
        return IBPT;
    }

    public void setIBPT(String IBPT) {
        this.IBPT = IBPT;
    }

    public String getIdToken() {
        return IdToken;
    }

    public void setIdToken(String IdToken) {
        this.IdToken = IdToken;
    }

    public String getCSC() {
        return CSC;
    }

    public void setCSC(String CSC) {
        this.CSC = CSC;
    }

        

}

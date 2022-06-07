/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.softsaj.egdvweb.Vendas.Payment.models;

import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Marcos
 */
@Entity
@Table(name = "preferences")
public class Root {
    
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long root;
    
    @Column(nullable = false, unique = false, length = 20)
    private String vendedor_id;
    
  public  String items;
    public String payer;
    public String paymentMethods;
    public String shipments;
    public String backUrls;
    public String id;
    public String initPoint;
    public String sandboxInitPoint;
    public String dateCreated;
    public String operationType;
    public String metadata;
    public String additionalInfo;
    public String externalReference;
    public boolean expires;
    public double collectorId;
    public double clientId;
    public String marketplace;
    public double marketplaceFee;
    public boolean binaryMode;

    public Root() {
        super();
    }

    public Root(Long root, String vendedor_id, String items, String payer, String paymentMethods, String shipments, String backUrls, String id, String initPoint, String sandboxInitPoint, String dateCreated, String operationType, String metadata, String additionalInfo, String externalReference, boolean expires, double collectorId, double clientId, String marketplace, double marketplaceFee, boolean binaryMode) {
        this.root = root;
        this.vendedor_id = vendedor_id;
        this.items = items;
        this.payer = payer;
        this.paymentMethods = paymentMethods;
        this.shipments = shipments;
        this.backUrls = backUrls;
        this.id = id;
        this.initPoint = initPoint;
        this.sandboxInitPoint = sandboxInitPoint;
        this.dateCreated = dateCreated;
        this.operationType = operationType;
        this.metadata = metadata;
        this.additionalInfo = additionalInfo;
        this.externalReference = externalReference;
        this.expires = expires;
        this.collectorId = collectorId;
        this.clientId = clientId;
        this.marketplace = marketplace;
        this.marketplaceFee = marketplaceFee;
        this.binaryMode = binaryMode;
    }

    public Long getRoot() {
        return root;
    }

    public void setRoot(Long root) {
        this.root = root;
    }

    public String getVendedor_id() {
        return vendedor_id;
    }

    public void setVendedor_id(String vendedor_id) {
        this.vendedor_id = vendedor_id;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(String paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public String getShipments() {
        return shipments;
    }

    public void setShipments(String shipments) {
        this.shipments = shipments;
    }

    public String getBackUrls() {
        return backUrls;
    }

    public void setBackUrls(String backUrls) {
        this.backUrls = backUrls;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInitPoint() {
        return initPoint;
    }

    public void setInitPoint(String initPoint) {
        this.initPoint = initPoint;
    }

    public String getSandboxInitPoint() {
        return sandboxInitPoint;
    }

    public void setSandboxInitPoint(String sandboxInitPoint) {
        this.sandboxInitPoint = sandboxInitPoint;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public boolean isExpires() {
        return expires;
    }

    public void setExpires(boolean expires) {
        this.expires = expires;
    }

    public double getCollectorId() {
        return collectorId;
    }

    public void setCollectorId(double collectorId) {
        this.collectorId = collectorId;
    }

    public double getClientId() {
        return clientId;
    }

    public void setClientId(double clientId) {
        this.clientId = clientId;
    }

    public String getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(String marketplace) {
        this.marketplace = marketplace;
    }

    public double getMarketplaceFee() {
        return marketplaceFee;
    }

    public void setMarketplaceFee(double marketplaceFee) {
        this.marketplaceFee = marketplaceFee;
    }

    public boolean isBinaryMode() {
        return binaryMode;
    }

    public void setBinaryMode(boolean binaryMode) {
        this.binaryMode = binaryMode;
    }
    
    

}

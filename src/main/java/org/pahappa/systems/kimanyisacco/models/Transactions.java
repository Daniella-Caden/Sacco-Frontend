package org.pahappa.systems.kimanyisacco.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

@Entity
@Table(name="transactions")
public class Transactions {
    private int id;

    private String transactionType;

    private int amount;

   

   
    private Members member;

    private String createdOn;
    private String status;
    private String notifications;


    

    @Column(name = "notifications",length = 500)
        public String getNotifications() {
            return notifications;
        }
    
        public void setNotifications(String notifications) {
            this.notifications = notifications;
        }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "transactionType", nullable = false, length = 255)
    public String getTransactionType() {
        return transactionType;
    }


    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Column(name = "amount", nullable = false, length = 255)
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        
        
        this.amount = amount;
    }


    
    @ManyToOne(fetch = FetchType.LAZY) // Assuming Many transactions belong to One member
    @JoinColumn(name = "userName", referencedColumnName = "userName")
    public Members getMember() {
        return member;
    }

    public void setMember(Members member) {
        this.member = member;
    }

    @Column(name = "createdOn")
    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
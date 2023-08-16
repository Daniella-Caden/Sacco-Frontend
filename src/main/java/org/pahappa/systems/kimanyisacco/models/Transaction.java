package org.pahappa.systems.kimanyisacco.models;

import java.time.LocalDate;

import javax.persistence.*;


import org.pahappa.systems.kimanyisacco.constants.TransactionType;
import org.pahappa.systems.kimanyisacco.constants.Status;



@Entity
@Table(name = "transactions")
public class Transaction {
    private long transactionId;

    private TransactionType transactionType;

    private double amount;

    private Member member;

    private LocalDate createdOn;
    private Status status;
    private String notifications;

    @Column(name = "notifications", length = 500)
    public String getNotifications() {
        return notifications;
    }

    public void setNotifications(String notifications) {
        this.notifications = notifications;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_Id")
    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    @Column(name = "transaction_type", nullable = false, length = 255)
    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Column(name = "amount", nullable = false, length = 255)
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {

        this.amount = amount;
    }

    @ManyToOne(fetch = FetchType.LAZY) // Assuming Many Transaction belong to One member
    @JoinColumn(name = "user_name", referencedColumnName = "user_name")
    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @Column(name = "created_on")
    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
package org.pahappa.systems.kimanyisacco.models;

import java.time.LocalDate;
import java.util.List;


import javax.persistence.*;
@Entity
@Table(name = "members")
public class Members {

    
    private long id;

    private String userName;
   

    private String password;
   

    private String status;
  

    private String firstName;
    private String lastName;
    private String location;
    private String dateOfBirth;
    private String email;
    private String telephoneContact;
    private String gender;
    private String currentEmployment;
    private String employerName;
    private String employerPhoneNumber;
    private String jobPosition;
    private double monthlySalary;
    private String sourcesOfIncome;
    private String refereeName;
    private String refereePhoneNumber;
    private String refereeJobPosition;
    private double accountBalance;

    // Constructors
    // Getters and Setters
    
// @OneToMany(fetch = FetchType.LAZY)
// private  List<Transactions> transactions;

// Constructors, getters, setters, and additional methods as needed

// Getter and Setter for transactions
// public List<Transactions> getTransactions() {
//     return transactions;
// }

// public void setTransactions( List<Transactions> transactions) {
//     this.transactions = transactions;
// }



    @Column(name = "firstName", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Id
    @Column(name = "userName", nullable = false)
     public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        
               this.userName = userName;
        
    }

@Column(name = "password", nullable = true)
     public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
       
        this.password = password;
    }
@Column(name = "status", nullable = true)
      public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
       
        this.status = status;
    }

   @Column(name = "accountBalance", nullable = true)
    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        
        this.accountBalance = accountBalance;
    }

    @Column(name = "lastName", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "location", nullable = false)
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name = "dateOfBirth", nullable = false)
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "telephoneContact", nullable = false)
    public String getTelephoneContact() {
        return telephoneContact;
    }

    public void setTelephoneContact(String telephoneContact) {
        this.telephoneContact = telephoneContact;
    }

    @Column(name = "gender", nullable = true)
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Column(name = "currentEmployment", nullable = false)
    public String getCurrentEmployment() {
        return currentEmployment;
    }

    public void setCurrentEmployment(String currentEmployment) {
        this.currentEmployment = currentEmployment;
    }

    @Column(name = "employerName", nullable = false)
    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    @Column(name = "employerPhoneNumber", nullable = false)
    public String getEmployerPhoneNumber() {
        return employerPhoneNumber;
    }

    public void setEmployerPhoneNumber(String employerPhoneNumber) {
        this.employerPhoneNumber = employerPhoneNumber;
    }

    @Column(name = "jobPosition", nullable = false)
    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    @Column(name = "monthlySalary", nullable = false)
    public double getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(double monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    @Column(name = "sourcesOfIncome", nullable = false) 
    public String getSourcesOfIncome() {
        return sourcesOfIncome;
    }

    public void setSourcesOfIncome(String sourcesOfIncome) {
        this.sourcesOfIncome = sourcesOfIncome;
    }

    @Column(name = "refereeName", nullable = false)
    public String getRefereeName() {
        return refereeName;
    }

    public void setRefereeName(String refereeName) {
        this.refereeName = refereeName;
    }

    @Column(name = "refereePhoneNumber", nullable = false)
    public String getRefereePhoneNumber() {
        return refereePhoneNumber;
    }

    public void setRefereePhoneNumber(String refereePhoneNumber) {
        this.refereePhoneNumber = refereePhoneNumber;
    }

    @Column(name = "refereeJobPosition", nullable = true)
    public String getRefereeJobPosition() {
        return refereeJobPosition;
    }

    public void setRefereeJobPosition(String refereeJobPosition) {
        this.refereeJobPosition = refereeJobPosition;
    }

    public void updateNotification(String userName2, int i) {
    }

    // Constructors if needed

    // Additional methods as needed

}

    


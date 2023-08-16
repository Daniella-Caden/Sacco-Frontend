package org.pahappa.systems.kimanyisacco.models;

import java.time.LocalDate;
import java.util.List;
import org.pahappa.systems.kimanyisacco.constants.*;
import javax.persistence.*;

import org.pahappa.systems.kimanyisacco.constants.Gender;

@Entity
@Table(name = "members")
public class Member {

    private String userName;

    private String password;

    private Status status;

    private String firstName;
    private String lastName;
    private String location;
    private LocalDate dateOfBirth;
    private String email;
    private String telephoneContact;
    private Gender gender;
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

    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Id
    @Column(name = "user_name", nullable = false)
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

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = true)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {

        this.status = status;
    }

    @Column(name = "last_name", nullable = false)
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

    @Column(name = "date_of_birth", nullable = false)
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "telephone_contact", nullable = false)
    public String getTelephoneContact() {
        return telephoneContact;
    }

    public void setTelephoneContact(String telephoneContact) {
        this.telephoneContact = telephoneContact;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = true)
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Column(name = "current_employment", nullable = false)
    public String getCurrentEmployment() {
        return currentEmployment;
    }

    public void setCurrentEmployment(String currentEmployment) {
        this.currentEmployment = currentEmployment;
    }

    @Column(name = "employer_name", nullable = false)
    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    @Column(name = "employer_phone_number", nullable = false)
    public String getEmployerPhoneNumber() {
        return employerPhoneNumber;
    }

    public void setEmployerPhoneNumber(String employerPhoneNumber) {
        this.employerPhoneNumber = employerPhoneNumber;
    }

    @Column(name = "job_position", nullable = false)
    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    @Column(name = "monthly_salary", nullable = false)
    public double getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(double monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    @Column(name = "source_of_income", nullable = false)
    public String getSourcesOfIncome() {
        return sourcesOfIncome;
    }

    public void setSourcesOfIncome(String sourcesOfIncome) {
        this.sourcesOfIncome = sourcesOfIncome;
    }

    @Column(name = "referee_name", nullable = false)
    public String getRefereeName() {
        return refereeName;
    }

    public void setRefereeName(String refereeName) {
        this.refereeName = refereeName;
    }

    @Column(name = "referee_phone_number", nullable = false)
    public String getRefereePhoneNumber() {
        return refereePhoneNumber;
    }

    public void setRefereePhoneNumber(String refereePhoneNumber) {
        this.refereePhoneNumber = refereePhoneNumber;
    }

    @Column(name = "referee_job_position", nullable = true)
    public String getRefereeJobPosition() {
        return refereeJobPosition;
    }

    public void setRefereeJobPosition(String refereeJobPosition) {
        this.refereeJobPosition = refereeJobPosition;
    }

}

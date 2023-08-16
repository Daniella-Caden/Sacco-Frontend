package org.pahappa.systems.kimanyisacco.models;

import java.lang.annotation.Inherited;


import javax.persistence.*;

@Entity
@Table(name="admins")
public class Admin{
private int adminId;
private String firstName;
private String lastName;
private String password;
private String email;

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="admin_id")
public int getAdminId() {
    return adminId;
}

public void setAdminId(int adminId) {
    this.adminId = adminId;
}

@Column(name="first_name")
public String getFirstName() {
    return firstName;
}

public void setFirstName(String firstName) {
    this.firstName = firstName;
}

@Column(name="last_name")
public String getLastName() {
    return lastName;
}

public void setLastName(String lastName) {
    this.lastName = lastName;
}


@Column(name="password")
public String getPassword() {
    return password;
}

public void setPassword(String password) {
    this.password = password;
}


@Column(name="email ")
public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}




}
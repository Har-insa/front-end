package com.hardis.connect.model;

/**
 * Created by a on 13/11/2015.
 */
public class User {
    private String firstName;
    private String lastname;
    private String email;
    private String password;
    private int agencyId;

    public User(String firstName, String lastname, String email, String password,int agencyId) {
        this.firstName = firstName;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.agencyId = agencyId;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getAgencyId() {
        return agencyId;
    }
}

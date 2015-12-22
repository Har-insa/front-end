package com.hardis.connect.model;

import java.io.Serializable;

/**
 * Created by a on 28/11/2015.
 */
public class Covoiturage implements Serializable{

    private int id;
    private int departureAgency;
    private int arrivalAgency;
    private String departureAgencyName;
    private String arrivalAgencyName;
    private String departureDate;
    private String arrivalDate;
    private String title;
    private int capacite;
    private String userName;
    private String dateCreation;
    private String timeStamp;
    private String email;


    public int getDepartureAgency() {
        return departureAgency;
    }

    public void setDepartureAgency(int departureAgency) {
        this.departureAgency = departureAgency;
    }

    public int getArrivalAgency() {
        return arrivalAgency;
    }

    public void setArrivalAgency(int arrivalAgency) {
        this.arrivalAgency = arrivalAgency;
    }

    public String getDepartureTime() {
        return departureDate;
    }

    public void setDepartureTime(String departureTime) {
        this.departureDate = departureTime;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public String getDepartureAgencyName() {
        return departureAgencyName;
    }

    public void setDepartureAgencyName(String departureAgencyName) {
        this.departureAgencyName = departureAgencyName;
    }

    public String getArrivalAgencyName() {
        return arrivalAgencyName;
    }

    public void setArrivalAgencyName(String arrivalAgencyName) {
        this.arrivalAgencyName = arrivalAgencyName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

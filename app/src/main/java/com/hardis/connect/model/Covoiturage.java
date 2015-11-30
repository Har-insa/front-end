package com.hardis.connect.model;

/**
 * Created by a on 28/11/2015.
 */
public class Covoiturage {

    private int departureAgency;
    private int arrivalAgency;
    private String departureAgencyName;
    private String arrivalAgencyName;
    private String departureDate;
    private String arrivalDate;
    private String title;
    private String description;
    private int capacite;
    private String userName;



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

    public String getArrivalTime() {
        return arrivalDate;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalDate = arrivalTime;
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
}

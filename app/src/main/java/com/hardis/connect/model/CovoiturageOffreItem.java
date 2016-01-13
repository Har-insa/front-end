package com.hardis.connect.model;


public class CovoiturageOffreItem  {
    private String userName;
    int imgResID;
    private String timeStamp;
    private String trajet;
    private String date;
    private String time;
    private String capacite;


    public CovoiturageOffreItem() {

    }

    public CovoiturageOffreItem(String userName, int imgResID, String timeStamp, String trajet, String date,String time,String capacite) {
        this.userName = userName;
        this.imgResID = imgResID;
        this.timeStamp = timeStamp;
        this.trajet = trajet;
        this.date = date;
        this.time=time;
        this.capacite = capacite;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getImgResID() {
        return imgResID;
    }

    public void setImgResID(int imgResID) {
        this.imgResID = imgResID;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTrajet() {
        return trajet;
    }

    public void setTrajet(String trajet) {
        this.trajet = trajet;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCapacite() {
        return capacite;
    }

    public void setCapacite(String capacite) {
        this.capacite = capacite;
    }


}

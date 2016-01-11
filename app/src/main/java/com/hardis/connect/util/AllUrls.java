package com.hardis.connect.util;

/**
 * Created by Hassan El Moutaraji on 25/08/2015.
 */
public class AllUrls {

    public static final String base="http://connext2.azurewebsites.net/api/";

    //---- Gestion des agences
    public static final String get_agencies_url=base+"agencies";


    //---- Gestion des utilisateurs
    public static final String add_user_url=base+"register";
    public static final String authenticate_user_url=base+"login";

    //---- Gestion des covoiturages
    public static final String add_covoiturage_url=base+"travels";
    public static final String get_offres_covoiturage_url=base+"travels";
    public static final String get_my_offres_covoiturage_url=base+"travels";
    public static final String book_offer_url=base+"requesttravels";



}



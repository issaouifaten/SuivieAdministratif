package com.example.suivieadministratif.model;

import java.util.Date;

public class RelationFournisseur {

    private Date   DatePiece   ;
    private  String  NumeroRelation  ;
    private   String  Contact   ;
    private   String   Representant  ;
    private   String MoyenRelation  ;
    private   String  NatureRelation  ;
    private   String  Etat   ;
    private   String  Objet   ;
    private   String  Object   ;  //  observation


    public RelationFournisseur(Date datePiece, String numeroRelation, String contact, String representant, String moyenRelation, String natureRelation, String etat, String objet, String object) {
        DatePiece = datePiece;
        NumeroRelation = numeroRelation;
        Contact = contact;
        Representant = representant;
        MoyenRelation = moyenRelation;
        NatureRelation = natureRelation;
        Etat = etat;
        Objet = objet;
        Object = object;
    }


    public Date getDatePiece() {
        return DatePiece;
    }

    public void setDatePiece(Date datePiece) {
        DatePiece = datePiece;
    }

    public String getNumeroRelation() {
        return NumeroRelation;
    }

    public void setNumeroRelation(String numeroRelation) {
        NumeroRelation = numeroRelation;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getRepresentant() {
        return Representant;
    }

    public void setRepresentant(String representant) {
        Representant = representant;
    }

    public String getMoyenRelation() {
        return MoyenRelation;
    }

    public void setMoyenRelation(String moyenRelation) {
        MoyenRelation = moyenRelation;
    }

    public String getNatureRelation() {
        return NatureRelation;
    }

    public void setNatureRelation(String natureRelation) {
        NatureRelation = natureRelation;
    }

    public String getEtat() {
        return Etat;
    }

    public void setEtat(String etat) {
        Etat = etat;
    }

    public String getObjet() {
        return Objet;
    }

    public void setObjet(String objet) {
        Objet = objet;
    }

    public String getObject() {
        return Object;
    }

    public void setObject(String object) {
        Object = object;
    }


    @Override
    public String toString() {
        return "RelationFournisseur{" +
                "DatePiece=" + DatePiece +
                ", NumeroRelation='" + NumeroRelation + '\'' +
                ", Contact='" + Contact + '\'' +
                ", Representant='" + Representant + '\'' +
                ", MoyenRelation='" + MoyenRelation + '\'' +
                ", NatureRelation='" + NatureRelation + '\'' +
                ", Etat='" + Etat + '\'' +
                ", Objet='" + Objet + '\'' +
                ", Object='" + Object + '\'' +
                '}';
    }
}

package com.example.suivieadministratif.model;

import java.util.Date;

public class AlimentationCaisseRecette {


    private String NumeroAlimentation;
    private Date DateAlimentation;
    private String CodeBanqueSociete;
    private String Origine;
    private String CodeCaisseDestination;
    private String Destination;
    private double TotalRecu;
    private String NomUtilisateur;
    private String NumeroEtat, Etat;
    private String CodeModeReglement, ModeRegelent, Reference;


    public AlimentationCaisseRecette(String numeroAlimentation, Date dateAlimentation, String codeBanqueSociete, String origine, String codeCaisseDestination, String destination,double TotalRecu, String nomUtilisateur, String numeroEtat, String etat, String codeModeReglement, String modeRegelent, String reference) {
        NumeroAlimentation = numeroAlimentation;
        DateAlimentation = dateAlimentation;
        CodeBanqueSociete = codeBanqueSociete;
        Origine = origine;
        CodeCaisseDestination = codeCaisseDestination;
        Destination = destination;
        this.TotalRecu=TotalRecu ;
        NomUtilisateur = nomUtilisateur;
        NumeroEtat = numeroEtat;
        Etat = etat;
        CodeModeReglement = codeModeReglement;
        ModeRegelent = modeRegelent;
        Reference = reference;
    }

    public String getNumeroAlimentation() {
        return NumeroAlimentation;
    }

    public void setNumeroAlimentation(String numeroAlimentation) {
        NumeroAlimentation = numeroAlimentation;
    }

    public Date getDateAlimentation() {
        return DateAlimentation;
    }

    public void setDateAlimentation(Date dateAlimentation) {
        DateAlimentation = dateAlimentation;
    }

    public String getCodeBanqueSociete() {
        return CodeBanqueSociete;
    }

    public void setCodeBanqueSociete(String codeBanqueSociete) {
        CodeBanqueSociete = codeBanqueSociete;
    }

    public String getOrigine() {
        return Origine;
    }

    public void setOrigine(String origine) {
        Origine = origine;
    }

    public String getCodeCaisseDestination() {
        return CodeCaisseDestination;
    }

    public void setCodeCaisseDestination(String codeCaisseDestination) {
        CodeCaisseDestination = codeCaisseDestination;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getNomUtilisateur() {
        return NomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        NomUtilisateur = nomUtilisateur;
    }

    public String getNumeroEtat() {
        return NumeroEtat;
    }

    public void setNumeroEtat(String numeroEtat) {
        NumeroEtat = numeroEtat;
    }

    public String getEtat() {
        return Etat;
    }

    public void setEtat(String etat) {
        Etat = etat;
    }

    public String getCodeModeReglement() {
        return CodeModeReglement;
    }

    public void setCodeModeReglement(String codeModeReglement) {
        CodeModeReglement = codeModeReglement;
    }

    public String getModeRegelent() {
        return ModeRegelent;
    }

    public void setModeRegelent(String modeRegelent) {
        ModeRegelent = modeRegelent;
    }

    public String getReference() {
        return Reference;
    }

    public void setReference(String reference) {
        Reference = reference;
    }


    public double getTotalRecu() {
        return TotalRecu;
    }

    public void setTotalRecu(double totalRecu) {
        TotalRecu = totalRecu;
    }



    @Override
    public String toString() {
        return "AlimentationCaisseRecette{" +
                "NumeroAlimentation='" + NumeroAlimentation + '\'' +
                ", DateAlimentation=" + DateAlimentation +
                ", CodeBanqueSociete='" + CodeBanqueSociete + '\'' +
                ", Origine='" + Origine + '\'' +
                ", CodeCaisseDestination='" + CodeCaisseDestination + '\'' +
                ", Destination='" + Destination + '\'' +
                ", NomUtilisateur='" + NomUtilisateur + '\'' +
                ", NumeroEtat='" + NumeroEtat + '\'' +
                ", Etat='" + Etat + '\'' +
                ", CodeModeReglement='" + CodeModeReglement + '\'' +
                ", ModeRegelent='" + ModeRegelent + '\'' +
                ", Reference='" + Reference + '\'' +
                '}';
    }
}



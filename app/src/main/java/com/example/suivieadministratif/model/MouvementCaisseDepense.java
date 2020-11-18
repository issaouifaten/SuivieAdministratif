package com.example.suivieadministratif.model;

import java.util.Date;

public class MouvementCaisseDepense {


    private String NumeroMouvement;
    private Date DateMouvement;
    private Double TotalPayer;
    private String NomUtilisateur;
    private Date HeureCreation;
    private String Observation;
    private String libelleDep;
    private String TypeDepense;


    public MouvementCaisseDepense(String numeroMouvement, Date dateMouvement, Double totalPayer, String nomUtilisateur, Date heureCreation, String observation, String libelleDep, String typeDepense) {
        NumeroMouvement = numeroMouvement;
        DateMouvement = dateMouvement;
        TotalPayer = totalPayer;
        NomUtilisateur = nomUtilisateur;
        HeureCreation = heureCreation;
        Observation = observation;
        this.libelleDep = libelleDep;
        TypeDepense = typeDepense;
    }


    public String getNumeroMouvement() {
        return NumeroMouvement;
    }

    public void setNumeroMouvement(String numeroMouvement) {
        NumeroMouvement = numeroMouvement;
    }

    public Date getDateMouvement() {
        return DateMouvement;
    }

    public void setDateMouvement(Date dateMouvement) {
        DateMouvement = dateMouvement;
    }

    public Double getTotalPayer() {
        return TotalPayer;
    }

    public void setTotalPayer(Double totalPayer) {
        TotalPayer = totalPayer;
    }

    public String getNomUtilisateur() {
        return NomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        NomUtilisateur = nomUtilisateur;
    }

    public Date getHeureCreation() {
        return HeureCreation;
    }

    public void setHeureCreation(Date heureCreation) {
        HeureCreation = heureCreation;
    }

    public String getObservation() {
        return Observation;
    }

    public void setObservation(String observation) {
        Observation = observation;
    }

    public String getLibelleDep() {
        return libelleDep;
    }

    public void setLibelleDep(String libelleDep) {
        this.libelleDep = libelleDep;
    }

    public String getTypeDepense() {
        return TypeDepense;
    }

    public void setTypeDepense(String typeDepense) {
        TypeDepense = typeDepense;
    }

    @Override
    public String toString() {
        return "MouvementCaisseDepense{" +
                "NumeroMouvement='" + NumeroMouvement + '\'' +
                ", DateMouvement=" + DateMouvement +
                ", TotalPayer=" + TotalPayer +
                ", NomUtilisateur='" + NomUtilisateur + '\'' +
                ", HeureCreation=" + HeureCreation +
                ", Observation='" + Observation + '\'' +
                ", libelleDep='" + libelleDep + '\'' +
                ", TypeDepense='" + TypeDepense + '\'' +
                '}';
    }
}

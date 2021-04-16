package com.example.suivieadministratif.model;

public class RecapEcheancierClient {

    private String annee;
    private String mois;
    private String LibelleMois;
    private double montant;

    public RecapEcheancierClient(String annee, String mois, String libelleMois, double montant) {
        this.annee = annee;
        this.mois = mois;
        LibelleMois = libelleMois;
        this.montant = montant;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public String getMois() {
        return mois;
    }

    public void setMois(String mois) {
        this.mois = mois;
    }

    public String getLibelleMois() {
        return LibelleMois;
    }

    public void setLibelleMois(String libelleMois) {
        LibelleMois = libelleMois;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    @Override
    public String toString() {
        return "RecapEcheancierClient{" +
                "annee='" + annee + '\'' +
                ", mois='" + mois + '\'' +
                ", LibelleMois='" + LibelleMois + '\'' +
                ", montant=" + montant +
                '}';
    }
}

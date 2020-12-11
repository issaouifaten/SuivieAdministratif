package com.example.suivieadministratif.model;

public class TypeMouvementClick {
    private   String  Libelle  ;
    private  int  nbrClick  ;
    private   double  total_montant ;

    public TypeMouvementClick(String libelle, int nbrClick ,double  total_montant) {
        Libelle = libelle;
        this.nbrClick = nbrClick;
        this.total_montant=total_montant  ;
    }

    public String getLibelle() {
        return Libelle;
    }

    public void setLibelle(String libelle) {
        Libelle = libelle;
    }

    public int getNbrClick() {
        return nbrClick;
    }

    public void setNbrClick(int nbrClick) {
        this.nbrClick = nbrClick;
    }

    public double getTotal_montant() {
        return total_montant;
    }

    public void setTotal_montant(double total_montant) {
        this.total_montant = total_montant;
    }

    @Override
    public String toString() {
        return "TypeMouvementClick{" +
                "Libelle='" + Libelle + '\'' +
                ", nbrClick=" + nbrClick +
                ", total_montant=" + total_montant +
                '}';
    }
}

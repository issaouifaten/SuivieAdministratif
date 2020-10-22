package com.example.suivieadministratif.model;

public class ChiffreAffaireParSociete {

    private   String  NonSociete  ;
    private   double  chiffreAffaire  ;


    public ChiffreAffaireParSociete(String nonSociete, double chiffreAffaire) {
        NonSociete = nonSociete;
        this.chiffreAffaire = chiffreAffaire;
    }


    public String getNonSociete() {
        return NonSociete;
    }

    public void setNonSociete(String nonSociete) {
        NonSociete = nonSociete;
    }

    public double getChiffreAffaire() {
        return chiffreAffaire;
    }

    public void setChiffreAffaire(double chiffreAffaire) {
        this.chiffreAffaire = chiffreAffaire;
    }

    @Override
    public String toString() {
        return "ChiffreAffaireParSociete{" +
                "NonSociete='" + NonSociete + '\'' +
                ", chiffreAffaire=" + chiffreAffaire +
                '}';
    }


}

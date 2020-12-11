package com.example.suivieadministratif.model;

public class Fournisseur {

    private   String  CodeFournisseur  ;
    private   String  RaisonSocial  ;


    public Fournisseur(String codeFournisseur, String raisonSocial) {
        CodeFournisseur = codeFournisseur;
        RaisonSocial = raisonSocial;
    }

    public String getCodeFournisseur() {
        return CodeFournisseur;
    }

    public void setCodeFournisseur(String codeFournisseur) {
        CodeFournisseur = codeFournisseur;
    }

    public String getRaisonSocial() {
        return RaisonSocial;
    }

    public void setRaisonSocial(String raisonSocial) {
        RaisonSocial = raisonSocial;
    }

    @Override
    public String toString() {
        return "Fournisseur{" +
                "CodeFournisseur='" + CodeFournisseur + '\'' +
                ", RaisonSocial='" + RaisonSocial + '\'' +
                '}';
    }
}

package com.example.suivieadministratif.model;

public class Fournisseur {

    private   String  CodeFournisseur  ;
    private   String  RaisonSocial  ;
    private   int  nbrClick   ;

    public Fournisseur(String codeFournisseur, String raisonSocial, int nbrClick) {
        CodeFournisseur = codeFournisseur;
        RaisonSocial = raisonSocial;
        this.nbrClick = nbrClick;
    }

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


    public int getNbrClick() {
        return nbrClick;
    }

    public void setNbrClick(int nbrClick) {
        this.nbrClick = nbrClick;
    }

    @Override
    public String toString() {
        return "Fournisseur{" +
                "CodeFournisseur='" + CodeFournisseur + '\'' +
                ", RaisonSocial='" + RaisonSocial + '\'' +
                '}';
    }
}

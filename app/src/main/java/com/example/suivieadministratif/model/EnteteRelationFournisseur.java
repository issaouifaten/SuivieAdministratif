package com.example.suivieadministratif.model;

import java.util.ArrayList;

public class EnteteRelationFournisseur {


    private    String  CodeFournisseur  ;
    private   String  RaisonSocialFournisseur  ;

    private ArrayList<RelationFournisseur>   listRelationFournisseur ;


    public EnteteRelationFournisseur(String codeFournisseur, String raisonSocialFournisseur) {
        CodeFournisseur = codeFournisseur;
        RaisonSocialFournisseur = raisonSocialFournisseur;
    }

    public String getCodeFournisseur() {
        return CodeFournisseur;
    }

    public void setCodeFournisseur(String codeFournisseur) {
        CodeFournisseur = codeFournisseur;
    }

    public String getRaisonSocialFournisseur() {
        return RaisonSocialFournisseur;
    }

    public void setRaisonSocialFournisseur(String raisonSocialFournisseur) {
        RaisonSocialFournisseur = raisonSocialFournisseur;
    }


    public ArrayList<RelationFournisseur> getListRelationFournisseur() {
        return listRelationFournisseur;
    }

    public void setListRelationFournisseur(ArrayList<RelationFournisseur> listRelationFournisseur) {
        this.listRelationFournisseur = listRelationFournisseur;
    }

    @Override
    public String toString() {
        return "EnteteRelationFournisseur{" +
                "CodeFournisseur='" + CodeFournisseur + '\'' +
                ", RaisonSocialFournisseur='" + RaisonSocialFournisseur + '\'' +
                '}';
    }
}

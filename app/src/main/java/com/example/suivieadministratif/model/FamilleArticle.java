package com.example.suivieadministratif.model;

public class FamilleArticle {

    private   String  CodeFamille  ;
    private   String  Libelle  ;


    public FamilleArticle(String codeFamille, String libelle) {
        CodeFamille = codeFamille;
        Libelle = libelle;
    }


    public String getCodeFamille() {
        return CodeFamille;
    }

    public void setCodeFamille(String codeFamille) {
        CodeFamille = codeFamille;
    }

    public String getLibelle() {
        return Libelle;
    }

    public void setLibelle(String libelle) {
        Libelle = libelle;
    }

    @Override
    public String toString() {
        return "FamilleArticle{" +
                "CodeFamille='" + CodeFamille + '\'' +
                ", Libelle='" + Libelle + '\'' +
                '}';
    }
}

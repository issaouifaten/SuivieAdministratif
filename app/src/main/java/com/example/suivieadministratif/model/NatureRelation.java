package com.example.suivieadministratif.model;

public class NatureRelation {

    private   String  CodeNatureRelation ;
    private   String  Libelle  ;

    public NatureRelation(String codeNatureRelation, String libelle) {
        CodeNatureRelation = codeNatureRelation;
        Libelle = libelle;
    }

    public String getCodeNatureRelation() {
        return CodeNatureRelation;
    }

    public void setCodeNatureRelation(String codeNatureRelation) {
        CodeNatureRelation = codeNatureRelation;
    }

    public String getLibelle() {
        return Libelle;
    }

    public void setLibelle(String libelle) {
        Libelle = libelle;
    }

    @Override
    public String toString() {
        return "NatureRelation{" +
                "CodeNatureRelation='" + CodeNatureRelation + '\'' +
                ", Libelle='" + Libelle + '\'' +
                '}';
    }
}

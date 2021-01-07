package com.example.suivieadministratif.model;

public class MoyenRelation {

    private  String CodeTypeRelation  ;
    private  String Libelle  ;


    public MoyenRelation(String codeTypeRelation, String libelle) {
        CodeTypeRelation = codeTypeRelation;
        Libelle = libelle;
    }

    public String getCodeTypeRelation() {
        return CodeTypeRelation;
    }

    public void setCodeTypeRelation(String codeTypeRelation) {
        CodeTypeRelation = codeTypeRelation;
    }

    public String getLibelle() {
        return Libelle;
    }

    public void setLibelle(String libelle) {
        Libelle = libelle;
    }

    @Override
    public String toString() {
        return "MoyenRelation{" +
                "CodeTypeRelation='" + CodeTypeRelation + '\'' +
                ", Libelle='" + Libelle + '\'' +
                '}';
    }
}

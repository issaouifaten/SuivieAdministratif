package com.example.suivieadministratif.model;

public class Depot {


    private   String  CodeDepot  ;
    private  String  Libelle  ;


    public Depot(String codeDepot, String libelle) {
        CodeDepot = codeDepot;
        Libelle = libelle;
    }


    public String getCodeDepot() {
        return CodeDepot;
    }

    public void setCodeDepot(String codeDepot) {
        CodeDepot = codeDepot;
    }

    public String getLibelle() {
        return Libelle;
    }

    public void setLibelle(String libelle) {
        Libelle = libelle;
    }


    @Override
    public String toString() {
        return "Depot{" +
                "CodeDepot='" + CodeDepot + '\'' +
                ", Libelle='" + Libelle + '\'' +
                '}';
    }
}

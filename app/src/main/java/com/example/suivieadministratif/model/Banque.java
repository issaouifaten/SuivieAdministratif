package com.example.suivieadministratif.model;

public class Banque {

    private   String  CodeBanque   ;
    private   String  Libelle   ;

    public Banque(String codeBanque, String libelle) {
        CodeBanque = codeBanque;
        Libelle = libelle;
    }

    public String getCodeBanque() {
        return CodeBanque;
    }

    public void setCodeBanque(String codeBanque) {
        CodeBanque = codeBanque;
    }

    public String getLibelle() {
        return Libelle;
    }

    public void setLibelle(String libelle) {
        Libelle = libelle;
    }

    @Override
    public String toString() {
        return "Banque{" +
                "CodeBanque='" + CodeBanque + '\'' +
                ", Libelle='" + Libelle + '\'' +
                '}';
    }
}

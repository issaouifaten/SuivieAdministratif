package com.example.suivieadministratif.model;

public class ModeReglement {

    private   String  CodeMode  ;
    private   String  Libelle   ;


    public ModeReglement(String codeMode, String libelle) {
        CodeMode = codeMode;
        Libelle = libelle;
    }


    public String getCodeMode() {
        return CodeMode;
    }

    public void setCodeMode(String codeMode) {
        CodeMode = codeMode;
    }

    public String getLibelle() {
        return Libelle;
    }

    public void setLibelle(String libelle) {
        Libelle = libelle;
    }

    @Override
    public String toString() {
        return "ModeReglement{" +
                "CodeMode='" + CodeMode + '\'' +
                ", Libelle='" + Libelle + '\'' +
                '}';
    }
}

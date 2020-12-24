package com.example.suivieadministratif.model;

public class ResponsableAdministration {


    private String  CodeResponsable  ;
    private String  Nom  ;


    public ResponsableAdministration(String codeResponsable, String nom) {
        CodeResponsable = codeResponsable;
        Nom = nom;
    }

    public String getCodeResponsable() {
        return CodeResponsable;
    }

    public void setCodeResponsable(String codeResponsable) {
        CodeResponsable = codeResponsable;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    @Override
    public String toString() {
        return "ResponsableAdministration{" +
                "CodeResponsable='" + CodeResponsable + '\'' +
                ", Nom='" + Nom + '\'' +
                '}';
    }
}

package com.example.suivieadministratif.model;

public class NatureArticle {

    private   String  CodeNature   ;
    private   String  LibelleNature  ;

    public NatureArticle(String codeNature, String libelleNature) {
        CodeNature = codeNature;
        LibelleNature = libelleNature;
    }

    public String getCodeNature() {
        return CodeNature;
    }

    public void setCodeNature(String codeNature) {
        CodeNature = codeNature;
    }

    public String getLibelleNature() {
        return LibelleNature;
    }

    public void setLibelleNature(String libelleNature) {
        LibelleNature = libelleNature;
    }


    @Override
    public String toString() {
        return "NatureArticle{" +
                "CodeNature='" + CodeNature + '\'' +
                ", LibelleNature='" + LibelleNature + '\'' +
                '}';
    }


}

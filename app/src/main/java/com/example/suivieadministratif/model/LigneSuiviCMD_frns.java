package com.example.suivieadministratif.model;

public class LigneSuiviCMD_frns {


    private String CodeArticle;
    private String Designation;
    private double MontantHT;
    private int qt_cmd;
    private int qt_livre;


    public LigneSuiviCMD_frns(String codeArticle, String designation, double montantHT, int qt_cmd, int qt_livre) {
        CodeArticle = codeArticle;
        Designation = designation;
        MontantHT = montantHT;
        this.qt_cmd = qt_cmd;
        this.qt_livre = qt_livre;
    }

    public String getCodeArticle() {
        return CodeArticle;
    }

    public void setCodeArticle(String codeArticle) {
        CodeArticle = codeArticle;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public double getMontantHT() {
        return MontantHT;
    }

    public void setMontantHT(double montantHT) {
        MontantHT = montantHT;
    }

    public int getQt_cmd() {
        return qt_cmd;
    }

    public void setQt_cmd(int qt_cmd) {
        this.qt_cmd = qt_cmd;
    }

    public int getQt_livre() {
        return qt_livre;
    }

    public void setQt_livre(int qt_livre) {
        this.qt_livre = qt_livre;
    }

    @Override
    public String toString() {
        return "LigneSuiviCMD_frns{" +
                "CodeArticle='" + CodeArticle + '\'' +
                ", Designation='" + Designation + '\'' +
                ", MontantHT=" + MontantHT +
                ", qt_cmd=" + qt_cmd +
                ", qt_livre=" + qt_livre +
                '}';
    }
}

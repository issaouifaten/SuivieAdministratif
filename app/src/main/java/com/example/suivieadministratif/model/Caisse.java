package com.example.suivieadministratif.model;

public class Caisse {

    private String CodeCompte;
    private String NomCompte;
    private String ModeReglement;
    private double Solde;
    private String TypePiece;


    public Caisse(String codeCompte, String nomCompte, String modeReglement, double solde, String typePiece) {
        CodeCompte = codeCompte;
        NomCompte = nomCompte;
        ModeReglement = modeReglement;
        Solde = solde;
        TypePiece = typePiece;

    }


    public String getCodeCompte() {
        return CodeCompte;
    }

    public void setCodeCompte(String codeCompte) {
        CodeCompte = codeCompte;
    }

    public String getNomCompte() {
        return NomCompte;
    }

    public void setNomCompte(String nomCompte) {
        NomCompte = nomCompte;
    }

    public String getModeReglement() {
        return ModeReglement;
    }

    public void setModeReglement(String modeReglement) {
        ModeReglement = modeReglement;
    }

    public double getSolde() {
        return Solde;
    }

    public void setSolde(double solde) {
        Solde = solde;
    }

    public String getTypePiece() {
        return TypePiece;
    }

    public void setTypePiece(String typePiece) {
        TypePiece = typePiece;
    }


    @Override
    public String toString() {
        return "Caisse{" +
                "CodeCompte='" + CodeCompte + '\'' +
                ", NomCompte='" + NomCompte + '\'' +
                ", ModeReglement='" + ModeReglement + '\'' +
                ", Solde=" + Solde +
                ", TypePiece='" + TypePiece + '\'' +
                '}';
    }
}

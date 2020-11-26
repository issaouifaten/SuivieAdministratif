package com.example.suivieadministratif.model;

import java.util.Date;

public class LigneReglementFournisseur {


    private String NumeroPiece;
    private String TypePiece;
    private Date datePiece;
    private double MontantPieceTTC;
    private double TotalRecu;
    private double TotalPayee;


    public LigneReglementFournisseur(String numeroPiece, String typePiece, Date datePiece, double montantPieceTTC, double totalRecu, double totalPayee ) {
        NumeroPiece = numeroPiece;
        TypePiece = typePiece;
        this.datePiece = datePiece;
        MontantPieceTTC = montantPieceTTC;
        TotalRecu = totalRecu;
        TotalPayee = totalPayee;

    }


    public String getNumeroPiece() {
        return NumeroPiece;
    }

    public void setNumeroPiece(String numeroPiece) {
        NumeroPiece = numeroPiece;
    }

    public String getTypePiece() {
        return TypePiece;
    }

    public void setTypePiece(String typePiece) {
        TypePiece = typePiece;
    }

    public Date getDatePiece() {
        return datePiece;
    }

    public void setDatePiece(Date datePiece) {
        this.datePiece = datePiece;
    }

    public double getMontantPieceTTC() {
        return MontantPieceTTC;
    }

    public void setMontantPieceTTC(double montantPieceTTC) {
        MontantPieceTTC = montantPieceTTC;
    }

    public double getTotalRecu() {
        return TotalRecu;
    }

    public void setTotalRecu(double totalRecu) {
        TotalRecu = totalRecu;
    }

    public double getTotalPayee() {
        return TotalPayee;
    }

    public void setTotalPayee(double totalPayee) {
        TotalPayee = totalPayee;
    }


    @Override
    public String toString() {
        return "LigneReglementClient{" +
                "NumeroPiece='" + NumeroPiece + '\'' +
                ", TypePiece='" + TypePiece + '\'' +
                ", datePiece=" + datePiece +
                ", MontantPieceTTC=" + MontantPieceTTC +
                ", TotalRecu=" + TotalRecu +
                ", TotalPayee=" + TotalPayee +

                '}';
    }
}

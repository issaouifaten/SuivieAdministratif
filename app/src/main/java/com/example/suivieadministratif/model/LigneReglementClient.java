package com.example.suivieadministratif.model;

import java.util.Date;

public class LigneReglementClient {


    private String NumeroPiece;
    private String TypePiece;
    private Date datePiece;
    private double MontantPieceTTC;
    private double TotalRecu;
    private double TotalPayee;
    private double TotalRetenu;


    public LigneReglementClient(String numeroPiece, String typePiece, Date datePiece, double montantPieceTTC, double totalRecu, double totalPayee, double totalRetenu) {
        NumeroPiece = numeroPiece;
        TypePiece = typePiece;
        this.datePiece = datePiece;
        MontantPieceTTC = montantPieceTTC;
        TotalRecu = totalRecu;
        TotalPayee = totalPayee;
        TotalRetenu = totalRetenu;
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

    public double getTotalRetenu() {
        return TotalRetenu;
    }

    public void setTotalRetenu(double totalRetenu) {
        TotalRetenu = totalRetenu;
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
                ", TotalRetenu=" + TotalRetenu +
                '}';
    }
}

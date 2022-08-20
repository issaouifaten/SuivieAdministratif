package com.example.suivieadministratif.model;

import java.util.Date;

public class RappelPayement {

    private   String  TypePiece  ;
    private   String  NumPiece  ;
    private   Date  datePiece   ;
    private   double  Montant  ;
    private   double  Reestant    ;


    public RappelPayement(String typePiece, String numPiece, Date datePiece, double montant, double reestant) {
        TypePiece = typePiece;
        NumPiece = numPiece;
        this.datePiece = datePiece;
        Montant = montant;
        Reestant = reestant;
    }

    public String getTypePiece() {
        return TypePiece;
    }

    public void setTypePiece(String typePiece) {
        TypePiece = typePiece;
    }

    public String getNumPiece() {
        return NumPiece;
    }

    public void setNumPiece(String numPiece) {
        NumPiece = numPiece;
    }

    public Date getDatePiece() {
        return datePiece;
    }

    public void setDatePiece(Date datePiece) {
        this.datePiece = datePiece;
    }

    public double getMontant() {
        return Montant;
    }

    public void setMontant(double montant) {
        Montant = montant;
    }

    public double getReestant() {
        return Reestant;
    }

    public void setReestant(double reestant) {
        Reestant = reestant;
    }

    @Override
    public String toString() {
        return "RappelPayement{" +
                "TypePiece='" + TypePiece + '\'' +
                ", NumPiece='" + NumPiece + '\'' +
                ", datePiece=" + datePiece +
                ", Montant=" + Montant +
                ", Reestant=" + Reestant +
                '}';
    }


}

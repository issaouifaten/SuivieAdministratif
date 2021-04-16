package com.example.suivieadministratif.model;

import java.util.Date;

public class FicheFournisseur {


    private Date datePiece;
    private String Operation;
    private String NumeroPiece;
    private double Debit;
    private double Credit;
    private double Solde;


    public FicheFournisseur(Date datePiece, String operation, String numeroPiece, double debit, double credit, double solde) {
        this.datePiece = datePiece;
        Operation = operation;
        NumeroPiece = numeroPiece;
        Debit = debit;
        Credit = credit;
        Solde = solde;
    }

    public Date getDatePiece() {
        return datePiece;
    }

    public void setDatePiece(Date datePiece) {
        this.datePiece = datePiece;
    }

    public String getOperation() {
        return Operation;
    }

    public void setOperation(String operation) {
        Operation = operation;
    }

    public String getNumeroPiece() {
        return NumeroPiece;
    }

    public void setNumeroPiece(String numeroPiece) {
        NumeroPiece = numeroPiece;
    }

    public double getDebit() {
        return Debit;
    }

    public void setDebit(double debit) {
        Debit = debit;
    }

    public double getCredit() {
        return Credit;
    }

    public void setCredit(double credit) {
        Credit = credit;
    }

    public double getSolde() {
        return Solde;
    }

    public void setSolde(double solde) {
        Solde = solde;
    }

    public void setSolde(int solde) {
        Solde = solde;
    }

    @Override
    public String toString() {
        return "FicheFournisseur{" +
                "datePiece=" + datePiece +
                ", Operation='" + Operation + '\'' +
                ", NumeroPiece='" + NumeroPiece + '\'' +
                ", Debit=" + Debit +
                ", Credit=" + Credit +
                '}';
    }
}

package com.example.suivieadministratif.model;

import java.util.Date;

public class TypeMouvement {

    private String NumeroPiece;
    private Date datePiece;
    private String Nom;
    private double Montant;
    private String   Type ;


    public TypeMouvement(String numeroPiece, Date datePiece, String nom, double montant ,String   Type) {
        NumeroPiece = numeroPiece;
        this.datePiece = datePiece;
        Nom = nom;
        Montant = montant;
        this.Type  =Type  ;
    }

    public String getNumeroPiece() {
        return NumeroPiece;
    }

    public void setNumeroPiece(String numeroPiece) {
        NumeroPiece = numeroPiece;
    }

    public Date getDatePiece() {
        return datePiece;
    }

    public void setDatePiece(Date datePiece) {
        this.datePiece = datePiece;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public double getMontant() {
        return Montant;
    }

    public void setMontant(double montant) {
        Montant = montant;
    }


    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    @Override
    public String toString() {

        return "TypeMouvement{" +
                "NumeroPiece='" + NumeroPiece + '\'' +
               // ", datePiece=" + datePiece +
                ", Nom='" + Nom + '\'' +
                  ", Type='" + Type + '\'' +
                 ", Montant=" + Montant +
                '}';
    }
}

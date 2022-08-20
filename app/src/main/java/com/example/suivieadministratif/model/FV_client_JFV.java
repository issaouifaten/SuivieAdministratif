package com.example.suivieadministratif.model;

import java.util.Date;

public class FV_client_JFV {

    private String NumeroPiece  ;
    private String CodeClient ;
    private   String RaisonSociale ;
    private Date DatePiece;
    private double TotalRemiseg;
    private double TotalHT ;
    private  double TotalTVA ;
    private double TimbreFiscal ;
    private  double TotalTTC ;


    public FV_client_JFV(String numeroPiece, String codeClient, String raisonSociale, Date datePiece, double totalRemiseg, double totalHT, double totalTVA, double timbreFiscal, double totalTTC) {
        NumeroPiece = numeroPiece;
        CodeClient = codeClient;
        RaisonSociale = raisonSociale;
        DatePiece = datePiece;
        TotalRemiseg = totalRemiseg;
        TotalHT = totalHT;
        TotalTVA = totalTVA;
        TimbreFiscal = timbreFiscal;
        TotalTTC = totalTTC;
    }

    public String getNumeroPiece() {
        return NumeroPiece;
    }

    public void setNumeroPiece(String numeroPiece) {
        NumeroPiece = numeroPiece;
    }

    public String getCodeClient() {
        return CodeClient;
    }

    public void setCodeClient(String codeClient) {
        CodeClient = codeClient;
    }

    public String getRaisonSociale() {
        return RaisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        RaisonSociale = raisonSociale;
    }

    public Date getDatePiece() {
        return DatePiece;
    }

    public void setDatePiece(Date datePiece) {
        DatePiece = datePiece;
    }

    public double getTotalRemiseg() {
        return TotalRemiseg;
    }

    public void setTotalRemiseg(double totalRemiseg) {
        TotalRemiseg = totalRemiseg;
    }

    public double getTotalHT() {
        return TotalHT;
    }

    public void setTotalHT(double totalHT) {
        TotalHT = totalHT;
    }

    public double getTotalTVA() {
        return TotalTVA;
    }

    public void setTotalTVA(double totalTVA) {
        TotalTVA = totalTVA;
    }

    public double getTimbreFiscal() {
        return TimbreFiscal;
    }

    public void setTimbreFiscal(double timbreFiscal) {
        TimbreFiscal = timbreFiscal;
    }

    public double getTotalTTC() {
        return TotalTTC;
    }

    public void setTotalTTC(double totalTTC) {
        TotalTTC = totalTTC;
    }
}

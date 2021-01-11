package com.example.suivieadministratif.model;

public class CaParPeriode {

    private   int   annee  ;
    private   int   mois  ;
    private   double   total  ;
    private   String Code   ;


    public CaParPeriode(int annee, int mois, double total, String code) {
        this.annee = annee;
        this.mois = mois;
        this.total = total;
        Code = code;
    }


    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public int getMois() {
        return mois;
    }

    public void setMois(int mois) {
        this.mois = mois;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    @Override
    public String toString() {

        return "CaParPeriode{" +
                "annee=" + annee +
                ", mois=" + mois +
                ", total=" + total +
                ", Code='" + Code + '\'' +
                '}';
    }
}

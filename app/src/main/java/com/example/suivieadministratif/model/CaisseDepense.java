package com.example.suivieadministratif.model;

public class CaisseDepense {


    private   String  LibelleCompte  ;
    private   double  Solde ;



    public CaisseDepense(String libelleCompte, double solde ) {

        LibelleCompte = libelleCompte;
        Solde = solde;

    }



    public String getLibelleCompte() {
        return LibelleCompte;
    }

    public void setLibelleCompte(String libelleCompte) {
        LibelleCompte = libelleCompte;
    }

    public double getSolde() {
        return Solde;
    }

    public void setSolde(double solde) {
        Solde = solde;
    }




    @Override
    public String toString() {
        return "CaisseDepense{" +

                ", LibelleCompte='" + LibelleCompte + '\'' +
                ", Solde=" + Solde +

                '}';
    }


}

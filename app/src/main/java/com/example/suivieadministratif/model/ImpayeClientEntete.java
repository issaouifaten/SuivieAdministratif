package com.example.suivieadministratif.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImpayeClientEntete {




    private  String  CodeClient   ;
    private  String   RaisonSociale   ;
    private  double   MontantImpaye  ;
    private  double   ResteAPayer  ;

    private ArrayList<ImpayeClient>  listImpayeClient ;


    public ImpayeClientEntete() {
    }

    public ImpayeClientEntete(String codeClient, String raisonSociale, double montantImpaye, double resteAPayer) {
        CodeClient = codeClient;
        RaisonSociale = raisonSociale;
        MontantImpaye = montantImpaye;
        ResteAPayer = resteAPayer;
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

    public double getMontantImpaye() {
        return MontantImpaye;
    }

    public void setMontantImpaye(double montantImpaye) {
        MontantImpaye = montantImpaye;
    }

    public double getResteAPayer() {
        return ResteAPayer;
    }

    public void setResteAPayer(double resteAPayer) {
        ResteAPayer = resteAPayer;
    }

    public ArrayList<ImpayeClient> getListImpayeClient() {
        return listImpayeClient;
    }

    public void setListImpayeClient(ArrayList<ImpayeClient> listImpayeClient) {
        this.listImpayeClient = listImpayeClient;
    }

    @Override
    public String toString() {
        return "ImpayeClientEntete{" +
                "CodeClient='" + CodeClient + '\'' +
                ", RaisonSociale='" + RaisonSociale + '\'' +
                ", MontantImpaye=" + MontantImpaye +
                ", ResteAPayer=" + ResteAPayer +
                '}';
    }
}

package com.example.suivieadministratif.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.model.ClientRegEtendu;
import com.example.suivieadministratif.model.MouvementCaisseDepense;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class ClientRecouvAdapterLV extends ArrayAdapter<ClientRegEtendu> {


    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

    DecimalFormat formatter = new DecimalFormat("0.000");
    private final Activity activity;
    private final ArrayList<ClientRegEtendu> listClientRegEtendu ;

    public ClientRecouvAdapterLV(Activity activity, ArrayList<ClientRegEtendu> listClientRegEtendu) {
        super(activity, R.layout.item_client_recouv_etendu  , listClientRegEtendu);
        // TODO Auto-generated constructor stub
        this.activity     = activity;
        this.listClientRegEtendu = listClientRegEtendu ;
    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        Context context = parent.getContext();
        View rowView = inflater.inflate (R.layout.item_client_recouv_etendu  , null, true);

        ClientRegEtendu  clientRegEtendu  = listClientRegEtendu.get(position);

        TextView txt_raison_social = (TextView) rowView.findViewById(R.id.txt_raison_social);
        TextView  txt_solde  = (TextView) rowView.findViewById(R.id.txt_solde);

        txt_raison_social.setText(clientRegEtendu.getRaisonSociale());
        txt_solde.setText(formatter.format(clientRegEtendu.getSolde())+" Dt");

        return rowView;

    }




}



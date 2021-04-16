package com.example.suivieadministratif.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.model.Client;
import com.example.suivieadministratif.module.vente.FicheClientActivity;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class ClientSelectAdapterRV extends RecyclerView.Adapter<ClientSelectAdapterRV.ViewHolder> {


    private final Activity activity;

    private final ArrayList<Client> listClient;  // = new ArrayList<>();
    String Origine;


    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    DateFormat HeureF = new SimpleDateFormat("HH:mm");
    DecimalFormat decFormat = new DecimalFormat("0.00");


    public ClientSelectAdapterRV(Activity activity, ArrayList<Client> listClient) {

        this.activity = activity;
        this.listClient = listClient;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_client, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Client client = listClient.get(position);

        holder.txt_code_client.setText(client.getCodeClient());
        holder.txt_raison_sociale.setText(client.getRaisonSociale());


         holder.btn_fiche_commertiale.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {


                 Intent  toFicheClient  = new Intent(activity  , FicheClientActivity.class)  ;
                 toFicheClient.putExtra( "CodeClient" ,client.getCodeClient()) ;
                 toFicheClient.putExtra( "RaisonSociale" ,client.getRaisonSociale()) ;
                 toFicheClient.putExtra( "TypeFiche" ,"Commertiale") ;
                 activity.startActivity(toFicheClient);

             }
         });



        holder.btn_fiche_comptable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent  toFicheClient  = new Intent(activity  , FicheClientActivity.class)  ;
                toFicheClient.putExtra( "CodeClient" ,client.getCodeClient()) ;
                toFicheClient.putExtra( "RaisonSociale" ,client.getRaisonSociale()) ;
                toFicheClient.putExtra( "TypeFiche" ,"Comptable") ;
                activity.startActivity(toFicheClient);

            }
        });

    }


    @Override
    public int getItemCount() {
        Log.e("size", "" + listClient.size());
        return listClient.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {



        public TextView txt_code_client;
        public TextView txt_raison_sociale;
        public LinearLayout  btn_fiche_commertiale ;
        public LinearLayout btn_fiche_comptable ;


        public ViewHolder(View itemView) {
            super(itemView);

            btn_fiche_commertiale = (LinearLayout) itemView.findViewById(R.id.btn_fiche_commertiale);
            btn_fiche_comptable = (LinearLayout) itemView.findViewById(R.id.btn_fiche_comptable);

            txt_code_client = (TextView) itemView.findViewById(R.id.txt_code_client);
            txt_raison_sociale = (TextView) itemView.findViewById(R.id.txt_raison_social);

        }

    }


}

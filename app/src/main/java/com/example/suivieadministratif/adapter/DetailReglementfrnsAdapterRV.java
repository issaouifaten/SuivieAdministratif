package com.example.suivieadministratif.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.model.DetailReglementClient;
import com.example.suivieadministratif.model.DetailReglementFournisseur;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class DetailReglementfrnsAdapterRV extends RecyclerView.Adapter<DetailReglementfrnsAdapterRV.ViewHolder> {


    private final Activity activity;
    private final ArrayList<DetailReglementFournisseur> listDetailReglementFournisseur ;//= new ArrayList<>();
    String Origine;

    public static String login;
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat decFormat = new DecimalFormat("0.000");


    public DetailReglementfrnsAdapterRV(Activity activity, ArrayList<DetailReglementFournisseur> listDetailReglementFournisseur, String Origine) {

        this.activity = activity;
        this.listDetailReglementFournisseur = listDetailReglementFournisseur;
        this.Origine = Origine;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_reg_client, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        DetailReglementFournisseur detailReglementFournisseur = listDetailReglementFournisseur.get(position);


        holder.txt_mode_reglement.setText(detailReglementFournisseur.getCodeModeReglement());
        holder.txt_reference.setText(detailReglementFournisseur.getReference());
        holder.txt_echeance.setText(df.format(detailReglementFournisseur.getEcheance()));
        holder.txt_montant_recu.setText(decFormat.format(detailReglementFournisseur.getMontantRecu()) + " DT ");


    }


    @Override
    public int getItemCount() {
        Log.e("size", "" + listDetailReglementFournisseur.size());
        return listDetailReglementFournisseur.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_mode_reglement;
        public TextView txt_reference;
        public TextView txt_echeance;
        public TextView txt_montant_recu;
        public ImageView btn_edit;

        public View view;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_mode_reglement = (TextView) itemView.findViewById(R.id.txt_mode_reglement);
            txt_reference = (TextView) itemView.findViewById(R.id.txt_reference);
            txt_echeance = (TextView) itemView.findViewById(R.id.txt_echeance);
            txt_montant_recu = (TextView) itemView.findViewById(R.id.txt_montant_recu);


            view = (View) itemView.findViewById(R.id.view);
            // card_item_client = (CardView) itemView.findViewById(R.id.card_item_client) ;

        }
    }

}

package com.example.suivieadministratif.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.model.LigneReglementClient;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class LigneReglementAdapterRV extends RecyclerView.Adapter<LigneReglementAdapterRV.ViewHolder> {


    private final Activity activity;
    private final ArrayList<LigneReglementClient> listLigneReglementClient;//= new ArrayList<>();
    String Origine;

    public static String login;
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat decFormat = new DecimalFormat("0.000");


    public LigneReglementAdapterRV(Activity activity, ArrayList<LigneReglementClient> listLigneReglementClient, String Origine) {

        this.activity = activity;
        this.listLigneReglementClient = listLigneReglementClient;
        this.Origine = Origine;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ligne_reg_client, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final LigneReglementClient ligneReglementClient = listLigneReglementClient.get(position);


        holder.txt_num_piece.setText(ligneReglementClient.getNumeroPiece());
        holder.txt_date_piece.setText(df.format(ligneReglementClient.getDatePiece()));
        holder.txt_montant_piece.setText(decFormat.format(ligneReglementClient.getMontantPieceTTC()));

        holder.txt_tot_recu_reg.setText(decFormat.format(ligneReglementClient.getTotalRecu()));
        holder.txt_montant_retenu.setText(decFormat.format(ligneReglementClient.getTotalRetenu()));


        holder.txt_total_payer_piece.setText(decFormat.format(ligneReglementClient.getTotalPayee()));




    }


    @Override
    public int getItemCount() {
        Log.e("size", "" + listLigneReglementClient.size());
        return listLigneReglementClient.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView txt_num_piece;
        public TextView txt_date_piece;
        public TextView txt_montant_piece;
        public TextView txt_tot_recu_reg;
        public TextView txt_montant_retenu;

        public TextView txt_tot_restant;
        public TextView txt_total_payer_piece;

        //  public CheckBox cb_client;

        public ViewHolder(View itemView) {
            super(itemView);
            //   card_item_client = (CardView) itemView.findViewById(R.id.card_item_client);
            //   cb_client = (CheckBox) itemView.findViewById(R.id.cb_select_client);

            txt_num_piece = (TextView) itemView.findViewById(R.id.txt_num_piece);
            txt_date_piece = (TextView) itemView.findViewById(R.id.txt_date_piece);
            txt_montant_piece = (TextView) itemView.findViewById(R.id.txt_montant_piece);

            txt_tot_recu_reg = (TextView) itemView.findViewById(R.id.txt_montant_recu_reg);
            txt_montant_retenu = (TextView) itemView.findViewById(R.id.txt_montant_retenu);

            txt_tot_restant = (TextView) itemView.findViewById(R.id.txt_montant_restant);

            txt_total_payer_piece = (TextView) itemView.findViewById(R.id.txt_montant_paye);


        }

    }

}

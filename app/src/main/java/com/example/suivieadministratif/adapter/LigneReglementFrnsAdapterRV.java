package com.example.suivieadministratif.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.model.LigneReglementClient;
import com.example.suivieadministratif.model.LigneReglementFournisseur;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class LigneReglementFrnsAdapterRV extends RecyclerView.Adapter<LigneReglementFrnsAdapterRV.ViewHolder> {


    private final Activity activity;
    private final ArrayList<LigneReglementFournisseur> listLigneReglementFournisseur;//= new ArrayList<>();
    String Origine;

    public static String login;
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat decFormat = new DecimalFormat("0.000");

    public LigneReglementFrnsAdapterRV(Activity activity, ArrayList<LigneReglementFournisseur> listLigneReglementFournisseur, String Origine) {

        this.activity = activity;
        this.listLigneReglementFournisseur = listLigneReglementFournisseur;
        this.Origine = Origine;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ligne_reg_frns, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final LigneReglementFournisseur ligneReglementFournisseur = listLigneReglementFournisseur.get(position);

        holder.txt_num_piece.setText(ligneReglementFournisseur.getNumeroPiece());
        holder.txt_date_piece.setText(df.format(ligneReglementFournisseur.getDatePiece()));
        holder.txt_montant_piece.setText(decFormat.format(ligneReglementFournisseur.getMontantPieceTTC()));

        holder.txt_tot_recu_reg.setText(decFormat.format(ligneReglementFournisseur.getTotalRecu()));
        //holder.txt_montant_retenu.setText(decFormat.format(ligneReglementFournisseur.getTotalRetenu()));
        holder.txt_total_payer_piece.setText(decFormat.format(ligneReglementFournisseur.getTotalPayee()));

    }


    @Override
    public int getItemCount() {
        Log.e("size", "" + listLigneReglementFournisseur.size());
        return listLigneReglementFournisseur.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_num_piece;
        public TextView txt_date_piece;
        public TextView txt_montant_piece;
        public TextView txt_tot_recu_reg;
       // public TextView txt_montant_retenu;

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
           // txt_montant_retenu = (TextView) itemView.findViewById(R.id.txt_montant_retenu);

            txt_tot_restant = (TextView) itemView.findViewById(R.id.txt_montant_restant);

            txt_total_payer_piece = (TextView) itemView.findViewById(R.id.txt_montant_paye);


        }

    }

}

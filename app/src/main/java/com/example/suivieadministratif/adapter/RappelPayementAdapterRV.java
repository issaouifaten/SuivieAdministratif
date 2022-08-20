package com.example.suivieadministratif.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.model.FicheClient;
import com.example.suivieadministratif.model.RappelPayement;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class RappelPayementAdapterRV extends RecyclerView.Adapter<RappelPayementAdapterRV.ViewHolder> {


    private final Activity activity;

    private final ArrayList<RappelPayement> listRappelPayement;


    public static String login;
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("0.000");


    public RappelPayementAdapterRV(Activity activity, ArrayList<RappelPayement> listRappelPayement) {

        this.activity = activity;
        this.listRappelPayement = listRappelPayement;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rappel_payement, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final RappelPayement rappel = listRappelPayement.get(position);

        holder.txt_operation.setText(rappel.getTypePiece());
        holder.txt_numero_piece.setText(rappel.getNumPiece()+ "");
        holder.txt_date_piece.setText(df.format(rappel.getDatePiece()) + "");


        final NumberFormat format = NumberFormat.getNumberInstance(Locale.FRENCH);
        format.setMinimumFractionDigits(3);
        format.setMaximumFractionDigits(3);



        holder.txt_montant.setText(format.format(rappel.getMontant())+ "");
        holder.txt_restant.setText(format.format(rappel.getReestant()) + "");



    }


    @Override
    public int getItemCount() {
        Log.e("size", "" + listRappelPayement.size());
        return listRappelPayement.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public CardView card_item_fiche_article;
        public TextView txt_operation;
        public TextView txt_numero_piece;


        public TextView txt_date_piece;

        public TextView txt_montant;
        public TextView txt_restant;



        public ViewHolder(View itemView) {
            super(itemView);

            card_item_fiche_article = (CardView) itemView.findViewById(R.id.item_rappel_payement);
            txt_operation = (TextView) itemView.findViewById(R.id.txt_operation);
            txt_numero_piece = (TextView) itemView.findViewById(R.id.txt_num_piece);
            txt_date_piece = (TextView) itemView.findViewById(R.id.txt_date_piece);

            txt_montant = (TextView) itemView.findViewById(R.id.txt_montant);
            txt_restant = (TextView) itemView.findViewById(R.id.txt_restant);




        }

    }

}

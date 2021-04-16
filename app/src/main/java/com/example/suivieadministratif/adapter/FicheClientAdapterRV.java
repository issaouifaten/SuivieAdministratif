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
import com.example.suivieadministratif.ui.statistique_rapport_activite.Client.FicheClientActivity2;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class FicheClientAdapterRV extends RecyclerView.Adapter<FicheClientAdapterRV.ViewHolder> {


    private final Activity activity;

    private final ArrayList<FicheClient> listFicheClient;


    public static String login;
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("0.000");


    public FicheClientAdapterRV(Activity activity, ArrayList<FicheClient> listFicheClient) {

        this.activity = activity;
        this.listFicheClient = listFicheClient;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fiche_article, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final FicheClient ficheClient = listFicheClient.get(position);

        holder.txt_operation.setText(ficheClient.getOperation());
        holder.txt_numero_piece.setText(ficheClient.getNumeroPiece() + "");
        holder.txt_date_piece.setText(df.format(ficheClient.getDatePiece()) + "");


        final NumberFormat format = NumberFormat.getNumberInstance(Locale.FRENCH);
        format.setMinimumFractionDigits(3);
        format.setMaximumFractionDigits(3);

        holder.txt_libelle_etree.setText("Débit");
        holder.txt_libelle_sortie.setText("Crédit");

        holder.txt_entee.setText(format.format(ficheClient.getDebit())+ "");
        holder.txt_sortie.setText(format.format(ficheClient.getCredit()) + "");
        holder.txt_solde.setText(format.format(ficheClient.getSolde())+"");


    }


    @Override
    public int getItemCount() {
        Log.e("size", "" + listFicheClient.size());
        return listFicheClient.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public CardView card_item_fiche_article;
        public TextView txt_operation;
        public TextView txt_numero_piece;
        public TextView txt_libelle_etree;
        public TextView txt_libelle_sortie;

        public TextView txt_date_piece;

        public TextView txt_entee;
        public TextView txt_sortie;
        public TextView txt_solde;


        public ViewHolder(View itemView) {
            super(itemView);

            card_item_fiche_article = (CardView) itemView.findViewById(R.id.item_rappel_payement);
            txt_operation = (TextView) itemView.findViewById(R.id.txt_operation);
            txt_numero_piece = (TextView) itemView.findViewById(R.id.txt_num_piece);
            txt_date_piece = (TextView) itemView.findViewById(R.id.txt_date_piece);

            txt_libelle_etree = (TextView) itemView.findViewById(R.id.txt_libelle_etree);
            txt_libelle_sortie = (TextView) itemView.findViewById(R.id.txt_libelle_sortie);


            txt_entee = (TextView) itemView.findViewById(R.id.txt_entree);
            txt_sortie = (TextView) itemView.findViewById(R.id.txt_sortie);
            txt_solde = (TextView) itemView.findViewById(R.id.txt_solde);


        }

    }

}

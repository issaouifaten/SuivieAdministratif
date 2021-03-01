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
import com.example.suivieadministratif.model.FicheArticle;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FicheArticleAdapterRV extends RecyclerView.Adapter<FicheArticleAdapterRV.ViewHolder> {


    private final Activity activity;

    private final ArrayList<FicheArticle> listFicheArticle;


    public static String login;
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("0.000");


    public FicheArticleAdapterRV(Activity activity, ArrayList<FicheArticle> listFicheArticle) {

        this.activity = activity;
        this.listFicheArticle = listFicheArticle;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fiche_article, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final FicheArticle ficheArticle = listFicheArticle.get(position);

        holder.txt_operation.setText(ficheArticle.getOperation());
        holder.txt_numero_piece.setText(ficheArticle.getNumeroPiece() + "");
        holder.txt_date_piece.setText(df.format(ficheArticle.getDatePiece()) + "");


        holder.txt_entee.setText(ficheArticle.getEntree() + "");
        holder.txt_sortie.setText(ficheArticle.getSortie() + "");

        holder.txt_solde.setText(ficheArticle.getSolde()+"");


    }


    @Override
    public int getItemCount() {
        Log.e("size", "" + listFicheArticle.size());
        return listFicheArticle.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public CardView card_item_fiche_article;
        public TextView txt_operation;
        public TextView txt_numero_piece;
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
            txt_entee = (TextView) itemView.findViewById(R.id.txt_entree);
            txt_sortie = (TextView) itemView.findViewById(R.id.txt_sortie);
            txt_solde = (TextView) itemView.findViewById(R.id.txt_solde);


        }

    }

}

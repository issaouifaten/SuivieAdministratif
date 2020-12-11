package com.example.suivieadministratif.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.model.ArticleFaibleRotation;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class ArticleFaibleRotationAdapterLV extends ArrayAdapter<ArticleFaibleRotation> {


    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    DecimalFormat formatter = new DecimalFormat("0.000");
    private final Activity activity;
    private final ArrayList<ArticleFaibleRotation> listArticleFaibleRotation;

    public ArticleFaibleRotationAdapterLV(Activity activity, ArrayList<ArticleFaibleRotation> listArticleFaibleRotation) {
        super(activity, R.layout.item_article_faible_rotation, listArticleFaibleRotation);
        // TODO Auto-generated constructor stub
        this.activity = activity;
        this.listArticleFaibleRotation = listArticleFaibleRotation;
    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        Context context = parent.getContext();
        View rowView = inflater.inflate(R.layout.item_article_faible_rotation, null, true);

        ArticleFaibleRotation artFaibleRot = listArticleFaibleRotation.get(position);


        TextView txt_designation = (TextView) rowView.findViewById(R.id.txt_des_art);
        TextView txt_code_art = (TextView) rowView.findViewById(R.id.txt_code_art);


        TextView txt_t_achat = (TextView) rowView.findViewById(R.id.txt_t_achat);
        TextView txt_t_vente = (TextView) rowView.findViewById(R.id.txt_t_vente);


        TextView txt_benefice = (TextView) rowView.findViewById(R.id.txt_benefice);
        TextView txt_taux_ca = (TextView) rowView.findViewById(R.id.txt_taux_ca);
        TextView txt_taux_rotation = (TextView) rowView.findViewById(R.id.txt_taux_rotation);
        TextView txt_coeff = (TextView) rowView.findViewById(R.id.txt_coeff);



        DecimalFormat decF = new DecimalFormat("0.000");

        try {

            txt_designation.setText(artFaibleRot.getDesignation());
            txt_code_art.setText( artFaibleRot.getCodeArticle());

            txt_t_achat .setText( decF.format(artFaibleRot.getPrixAchatHT() )  );
            txt_t_vente .setText( decF.format(artFaibleRot.getValeurVenteHT()  )  );

            txt_benefice.setText(decF.format(artFaibleRot.getTauxBenifice()) +""  );
            txt_taux_ca.setText( decF.format(artFaibleRot.getTauxCA()  )  );

            txt_taux_rotation.setText(  decF.format( artFaibleRot.getTauxRotation()) +"");
            txt_coeff.setText(decF.format(artFaibleRot.getCoeff()) +"");


        } catch (Exception ex) {
            Log.e("ERROR_echeance_client", ex.getMessage().toString());
        }


/*
        TextView txt_num_piece = (TextView) rowView.findViewById(R.id.txt_piece);
        TextView txt_date_piece = (TextView) rowView.findViewById(R.id.txt_date_piece);


        TextView txt_code_client  = (TextView) rowView.findViewById(R.id.txt_code_client);
        TextView txt_raison_client  = (TextView) rowView.findViewById(R.id.txt_raison_client);


        TextView txt_code_article  = (TextView) rowView.findViewById(R.id.txt_code_article);
        TextView txt_design_article  = (TextView) rowView.findViewById(R.id.txt_design_article);
        TextView txt_qt_article  = (TextView) rowView.findViewById(R.id.txt_qt);


        TextView  txt_fournisseur = (TextView) rowView.findViewById(R.id.txt_frns);

        TextView txt_montant = (TextView) rowView.findViewById(R.id.txt_total_montant);



*/

        return rowView;

    }


}



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
import com.example.suivieadministratif.model.MouvementVenteService;
import com.example.suivieadministratif.model.ReglementClient;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class MvmentVenteServiceAdapterLV extends ArrayAdapter<MouvementVenteService> {


    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    DecimalFormat formatter = new DecimalFormat("0.000");
    private final Activity activity;
    private final ArrayList<MouvementVenteService> listMvmentVenteService;

    public MvmentVenteServiceAdapterLV(Activity activity, ArrayList<MouvementVenteService> listMvmentVenteService) {
        super(activity, R.layout.item_mvment_vente_service , listMvmentVenteService);
        // TODO Auto-generated constructor stub
        this.activity     = activity;
        this.listMvmentVenteService = listMvmentVenteService;
    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        Context context = parent.getContext();
        View rowView = inflater.inflate (R.layout.item_mvment_vente_service  , null, true);

        MouvementVenteService  mvs = listMvmentVenteService.get(position);


        TextView txt_num_piece = (TextView) rowView.findViewById(R.id.txt_piece);
        TextView txt_date_piece = (TextView) rowView.findViewById(R.id.txt_date_piece);


        TextView txt_code_client  = (TextView) rowView.findViewById(R.id.txt_code_client);
        TextView txt_raison_client  = (TextView) rowView.findViewById(R.id.txt_raison_client);


        TextView txt_code_article  = (TextView) rowView.findViewById(R.id.txt_code_article);
        TextView txt_design_article  = (TextView) rowView.findViewById(R.id.txt_design_article);
        TextView txt_qt_article  = (TextView) rowView.findViewById(R.id.txt_qt);


        TextView  txt_fournisseur = (TextView) rowView.findViewById(R.id.txt_frns);
        TextView txt_nom_utilisateur = (TextView) rowView.findViewById(R.id.txt_etablie_par);
        TextView txt_montant = (TextView) rowView.findViewById(R.id.txt_total_montant);


try {
    txt_num_piece.setText(mvs.getNumeroPiece());
    txt_date_piece.setText(sdf.format(mvs.getDatePiece()));
    txt_code_client.setText("("+mvs.getCodeClient()+")");
    txt_raison_client.setText(mvs.getRaisonSociale());
    txt_code_article.setText("("+mvs.getCodeArticle()+")");
    txt_design_article.setText(mvs.getDesignationArticle());
    txt_qt_article.setText(mvs.getQuantite()+"");
    txt_fournisseur.setText(mvs.getFrs());
    txt_nom_utilisateur.setText(mvs.getNomUtilisateur());
    txt_montant.setText(formatter.format(mvs.getMontantTTC())+" Dt");


}
catch (Exception ex)

{
    Log.e("ERROR_msv",ex.getMessage().toString())  ;
}


        return rowView;

    }




}



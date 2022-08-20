package com.example.suivieadministratif.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.model.AlimentationCaisseRecette;
import com.example.suivieadministratif.model.EcheanceClient;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


public class AlimentationCaisseRecetteAdapterLV extends ArrayAdapter<AlimentationCaisseRecette> {


    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    DecimalFormat formatter = new DecimalFormat("0.000");
    private final Activity activity;
    private final ArrayList<AlimentationCaisseRecette> listAlimentationCaisseRecette;
    String  origine  ;

    public AlimentationCaisseRecetteAdapterLV(Activity activity, ArrayList<AlimentationCaisseRecette> listAlimentationCaisseRecette ,String  origine ) {
        super(activity, R.layout.item_alimentation_caisse, listAlimentationCaisseRecette);
        // TODO Auto-generated constructor stub
        this.activity     = activity;
        this.listAlimentationCaisseRecette = listAlimentationCaisseRecette;
        this.origine=origine;
    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        Context context = parent.getContext();
        View rowView = inflater.inflate (R.layout.item_alimentation_caisse  , null, true);

        AlimentationCaisseRecette  alimen = listAlimentationCaisseRecette.get(position);



        TextView  txt_num_alim= (TextView) rowView.findViewById(R.id.txt_num_alimentation);
        TextView txt_date_alimentation = (TextView) rowView.findViewById(R.id.txt_date_alimentation);
        TextView txt_origine = (TextView) rowView.findViewById(R.id.txt_origine);
        TextView txt_destination = (TextView) rowView.findViewById(R.id.txt_destination);

        TextView txt_montant_recu = (TextView) rowView.findViewById(R.id.txt_montant_recu);

        TextView txt_mode_reglement = (TextView) rowView.findViewById(R.id.txt_mode_reglement);
        TextView txt_reference  = (TextView) rowView.findViewById(R.id.txt_reference);
        TextView txt_etablie_par = (TextView) rowView.findViewById(R.id.txt_etablie_par);
        TextView txt_libelle_etat = (TextView) rowView.findViewById(R.id.txt_libelle_etat);


        ImageView  img_origine  = (ImageView)  rowView.findViewById(R.id.img_origine);
        ImageView  img_destination  = (ImageView)  rowView.findViewById(R.id.img_destination);

        try {

            if (origine.equals("alimentation_caisse"))
            {
                img_origine.setImageResource(R.drawable.ic_caisse);
                img_destination.setImageResource(R.drawable.ic_depense);

            }

            if (alimen.getDestination().contains("GERANT"))
            {
                img_destination.setImageResource(R.drawable.manager);
            }

            txt_num_alim.setText(alimen.getNumeroAlimentation());
            txt_date_alimentation.setText(sdf.format(alimen.getDateAlimentation()));

            txt_origine.setText(alimen.getOrigine());
            txt_destination.setText(alimen.getDestination());

            final NumberFormat instance = NumberFormat.getNumberInstance(Locale.FRENCH);
            instance.setMinimumFractionDigits(3);
            instance.setMaximumFractionDigits(3);


            txt_montant_recu.setText(instance.format(alimen.getTotalRecu())+" Dt");

            txt_mode_reglement.setText(alimen.getModeRegelent());
            txt_reference.setText(alimen.getReference());

            txt_etablie_par.setText(alimen.getNomUtilisateur());

            txt_libelle_etat.setText(alimen.getEtat());


        }
        catch (Exception ex)
        {
            Log.e("ERROR_echeance_client",ex.getMessage().toString())  ;
        }




        return rowView;

    }




}



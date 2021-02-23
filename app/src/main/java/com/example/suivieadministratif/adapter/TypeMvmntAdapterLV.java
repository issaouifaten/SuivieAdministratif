package com.example.suivieadministratif.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.model.TypeMouvement;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class TypeMvmntAdapterLV extends ArrayAdapter<TypeMouvement> {


    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    DecimalFormat formatter = new DecimalFormat("0.000");
    private final Activity activity;
    private final ArrayList<TypeMouvement> listTypeMouvement;

    public TypeMvmntAdapterLV(Activity activity, ArrayList<TypeMouvement> listTypeMouvement) {
        super(activity, R.layout.item_type_mvmnt, listTypeMouvement);
        // TODO Auto-generated constructor stub
        this.activity = activity;
        this.listTypeMouvement = listTypeMouvement;
    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();
        Context context = parent.getContext();
        View rowView = null;
        TypeMouvement tmvmnt = listTypeMouvement.get(position);

        if (tmvmnt.getNumeroPiece().equals("")) {

            rowView = inflater.inflate(R.layout.item_titre_type, null, true);

            TextView  txt_titre_type_mvmnt = rowView.findViewById(R.id.txt_titre_type_mvmnt);
            TextView  txt_total_montant= rowView.findViewById(R.id.txt_total_montant);

            txt_titre_type_mvmnt.setText(tmvmnt.getType().replace("Type Mouvement ", ""));
            txt_total_montant.setText(formatter.format(tmvmnt.getMontant()));

        }

        else if (!tmvmnt.getNumeroPiece().equals(""))  {

            rowView = inflater.inflate(R.layout.item_type_mvmnt, null, true);
            TextView txt_num_piece = rowView.findViewById(R.id.txt_num_piece);
            TextView txt_nom = rowView.findViewById(R.id.txt_nom);
            TextView txt_montant = rowView.findViewById(R.id.txt_montant);

            txt_num_piece.setText(tmvmnt.getNumeroPiece());
            txt_nom.setText(tmvmnt.getNom());
            txt_montant.setText(formatter.format(tmvmnt.getMontant()));

        }

        return rowView;

    }

}
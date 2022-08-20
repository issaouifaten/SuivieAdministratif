package com.example.suivieadministratif.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.model.Article_BL_JAV;
import com.example.suivieadministratif.model.ImpayeClient;
import com.example.suivieadministratif.model.ImpayeClientEntete;
import com.example.suivieadministratif.model.ImpayeFournisseur;
import com.example.suivieadministratif.model.ImpayeFournisseurEntete;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ImpayeFournisseurExtensibleAdapter extends BaseExpandableListAdapter {

    private Activity activity;
    private ArrayList<ImpayeFournisseurEntete> listimpayeFournisseurEntetes ;
    public LayoutInflater inflater;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    public ImpayeFournisseurExtensibleAdapter(Activity activity, ArrayList<ImpayeFournisseurEntete> listimpayeFournisseurEntetes) {

        this.activity = activity;
        this.listimpayeFournisseurEntetes = listimpayeFournisseurEntetes;
        inflater = activity.getLayoutInflater();
        Log.e("adapter", "RemiseExpandableListAdapter");

    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.item_entete_impaye_frns, null);
        ImpayeFournisseurEntete  imp_frns_entete  = (ImpayeFournisseurEntete) getGroup(groupPosition);
       // Log.e("getViewGroup", "" + clientJAV.toString());


        CardView item_gratuite_entete = (CardView) convertView.findViewById(R.id.item_gratuite_entete);


        TextView txt_raison = (TextView) convertView.findViewById(R.id.txt_raison_social);
        TextView txt_mnt_impye = (TextView) convertView.findViewById(R.id.txt_montant_impaye);


        txt_raison.setText(imp_frns_entete.getRaisonSociale());


        final NumberFormat decF = NumberFormat.getNumberInstance(Locale.FRENCH);
        decF.setMinimumFractionDigits(3);
        decF.setMaximumFractionDigits(3);


        txt_mnt_impye.setText(decF.format(imp_frns_entete.getMontantImpayeFournisseur()));



        if (groupPosition % 2 == 0) // paire
        {
            item_gratuite_entete.setCardBackgroundColor(activity.getResources().getColor(R.color.rouge_clair));
        } else {
            item_gratuite_entete.setCardBackgroundColor(activity.getResources().getColor(R.color.white));
        }


        return convertView;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.listimpayeFournisseurEntetes.get(listPosition).getList_imp_frns().get(expandedListPosition);

     /*   return this.expandableListDetail.get(this.listClientRemise.get(listPosition))
                .get(expandedListPosition);*/
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View rowView, ViewGroup parent) {


        rowView = inflater.inflate(R.layout.item_impaye_fournisseur, null);

        TextView txt_raison_sociale= (TextView) rowView.findViewById(R.id.txt_raison);
        TextView txt_date_impaye = (TextView) rowView.findViewById(R.id.txt_date_impaye);

        TextView txt_num_impaye = (TextView) rowView.findViewById(R.id.txt_num_impaye);
        TextView txt_montant_impaye = (TextView) rowView.findViewById(R.id.txt_montant_impaye);


        final  ImpayeFournisseur imp_Frns = (ImpayeFournisseur) getChild(groupPosition, childPosition);

        try {

            txt_raison_sociale.setText(imp_Frns.getRaisonSociale());
            txt_date_impaye.setText(sdf.format(imp_Frns.getDateImpayeFournisseur()));
            final NumberFormat decF = NumberFormat.getNumberInstance(Locale.FRENCH);
            decF.setMinimumFractionDigits(3);
            decF.setMaximumFractionDigits(3);

            txt_num_impaye.setText(imp_Frns.getNumeroImpayeFournisseur());
            txt_montant_impaye.setText(decF.format(imp_Frns.getMontantImpayeFournisseur()) );

        }
        catch (Exception ex)
        {
            Log.e("ERROR_echeance_client",ex.getMessage().toString())  ;
        }



        return rowView;
    }

    @Override
    public int getChildrenCount(int listPosition) {

        return this.listimpayeFournisseurEntetes.get(listPosition).getList_imp_frns().size();

        /* return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)) .size();*/
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.listimpayeFournisseurEntetes.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listimpayeFournisseurEntetes.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }


    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }


    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }



}
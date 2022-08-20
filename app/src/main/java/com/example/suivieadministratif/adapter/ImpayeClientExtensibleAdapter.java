package com.example.suivieadministratif.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.model.Article_BL_JAV;
import com.example.suivieadministratif.model.Bl_Client_JAV;
import com.example.suivieadministratif.model.ClientJAV;
import com.example.suivieadministratif.model.ImpayeClient;
import com.example.suivieadministratif.model.ImpayeClientEntete;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ImpayeClientExtensibleAdapter extends BaseExpandableListAdapter {

    private Activity activity;
    private ArrayList<ImpayeClientEntete> listimpayeClient;
    public LayoutInflater inflater;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    public ImpayeClientExtensibleAdapter(Activity activity, ArrayList<ImpayeClientEntete> listimpayeClient) {
        this.activity = activity;
        this.listimpayeClient = listimpayeClient;
        inflater = activity.getLayoutInflater();
        Log.e("adapter", "RemiseExpandableListAdapter");
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.item_entete_impaye_client, null);
        ImpayeClientEntete imp_client_entete  = (ImpayeClientEntete) getGroup(groupPosition);
       // Log.e("getViewGroup", "" + clientJAV.toString());

        CardView item_gratuite_entete = (CardView) convertView.findViewById(R.id.item_gratuite_entete);

        TextView txt_raison = (TextView) convertView.findViewById(R.id.txt_raison_social);
        TextView txt_mnt_impye = (TextView) convertView.findViewById(R.id.txt_montant_impaye);
        TextView txt_reste_imp= (TextView) convertView.findViewById(R.id.txt_reste_a_paye);

        txt_raison.setText(imp_client_entete.getRaisonSociale());

        final NumberFormat decF = NumberFormat.getNumberInstance(Locale.FRENCH);
        decF.setMinimumFractionDigits(3);
        decF.setMaximumFractionDigits(3);


        txt_mnt_impye.setText(decF.format(imp_client_entete.getMontantImpaye()));
        txt_reste_imp.setText(decF.format(imp_client_entete.getResteAPayer()));


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
        return this.listimpayeClient.get(listPosition).getListImpayeClient().get(expandedListPosition);

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


        rowView = inflater.inflate(R.layout.item_impaye_client, null);

        final ImpayeClient imp_client = (ImpayeClient) getChild(groupPosition, childPosition);

        TextView txt_raison_sociale= (TextView) rowView.findViewById(R.id.txt_raison);
        TextView txt_date_echenace = (TextView) rowView.findViewById(R.id.txt_date_echeance);


        TextView txt_reference = (TextView) rowView.findViewById(R.id.txt_reference);

        TextView txt_mode_reg = (TextView) rowView.findViewById(R.id.txt_mode_reg);
        TextView txt_montant_impaye = (TextView) rowView.findViewById(R.id.txt_montant_impaye);
        TextView txt_reste_a_payer = (TextView) rowView.findViewById(R.id.txt_reste_a_paye);

        TextView txt_banque= (TextView) rowView.findViewById(R.id.txt_banque);
        TextView txt_etat  = (TextView) rowView.findViewById(R.id.txt_etat);

        try {


            final NumberFormat decF = NumberFormat.getNumberInstance(Locale.FRENCH);
            decF.setMinimumFractionDigits(3);
            decF.setMaximumFractionDigits(3);


            txt_raison_sociale.setText(imp_client.getRaisonSociale());
            txt_date_echenace.setText(sdf.format(imp_client.getEcheance()));

            txt_reference .setText(imp_client.getReference());
            txt_mode_reg.setText(imp_client.getModeReglement());
            txt_montant_impaye.setText(decF.format(imp_client.getMontantImpaye()) );
            txt_reste_a_payer.setText(decF.format(imp_client.getResteAPayer()) );
            txt_banque.setText(imp_client.getBanque());
            txt_etat.setText(imp_client.getEtat());
        }
        catch (Exception ex)
        {
            Log.e("ERROR_echeance_client",ex.getMessage().toString())  ;
        }

        return rowView;
    }

    @Override
    public int getChildrenCount(int listPosition) {


        return this.listimpayeClient.get(listPosition).getListImpayeClient().size();

        /* return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)) .size();*/
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.listimpayeClient.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listimpayeClient.size();
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



    public class Detail_BL_Adapter1 extends ArrayAdapter<Article_BL_JAV> {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        private final ArrayList<Article_BL_JAV> list_ligne_bl;

        public Detail_BL_Adapter1(ArrayList<Article_BL_JAV> list_ligne_bl) {
            super(activity, R.layout.item_detail_bl, list_ligne_bl);

            this.list_ligne_bl = list_ligne_bl;

            Log.e("adapter", "Constructor" + list_ligne_bl.toString());

        }

        public View getView(int position, View view, ViewGroup parent) {

            LayoutInflater inflater = activity.getLayoutInflater();

            View rowView = inflater.inflate(R.layout.item_detail_bl, null, true);

            Article_BL_JAV lbl = list_ligne_bl.get(position);

            TextView txt_code_art = (TextView) rowView.findViewById(R.id.txt_code_art);
            TextView txt_designation = (TextView) rowView.findViewById(R.id.txt_des_art);
            TextView txt_qt = (TextView) rowView.findViewById(R.id.txt_qt);
            TextView txt_ttc = (TextView) rowView.findViewById(R.id.txt_ttc);

            txt_code_art.setText(lbl.getCodeArticle());
            txt_designation.setText(lbl.getDesignationArticle());
            txt_qt.setText(lbl.getQuantite() + "");

            DecimalFormat  decF  = new DecimalFormat("0.000")  ;
            txt_ttc.setText(decF.format(lbl.getMontantTTC())+ "");
            return rowView;

        }

    }


}
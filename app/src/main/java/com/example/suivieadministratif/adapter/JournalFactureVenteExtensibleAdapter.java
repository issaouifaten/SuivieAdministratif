package com.example.suivieadministratif.adapter;

import android.app.Activity;
import android.app.FragmentManager;
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
import com.example.suivieadministratif.dialog.DialogDetailFactureVente;
import com.example.suivieadministratif.model.Article_BL_JAV;
import com.example.suivieadministratif.model.Bl_Client_JAV;
import com.example.suivieadministratif.model.ClientJAV;
import com.example.suivieadministratif.model.ClientJFV;
import com.example.suivieadministratif.model.FV_client_JFV;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class JournalFactureVenteExtensibleAdapter extends BaseExpandableListAdapter {

    private Activity activity;
    private ArrayList<ClientJFV> listClientJFV;
    public LayoutInflater inflater;


    public JournalFactureVenteExtensibleAdapter(Activity activity, ArrayList<ClientJFV> listClientJFV) {
        this.activity = activity;
        this.listClientJFV = listClientJFV;
        inflater = activity.getLayoutInflater();
        Log.e("adapter", "RemiseExpandableListAdapter");
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.item_entete_jfv, null);
        ClientJFV clientJFV = (ClientJFV) getGroup(groupPosition);
        // Log.e("getViewGroup", "" + clientJAV.toString());


        CardView item_gratuite_entete = (CardView) convertView.findViewById(R.id.item_gratuite_entete);


        TextView txt_raison = (TextView) convertView.findViewById(R.id.txt_raison_social);

        TextView txt_total_ttc_client = (TextView) convertView.findViewById(R.id.txt_total_ttc_client);


        txt_raison.setText(clientJFV.getRaisonSociale());


        DecimalFormat decF = new DecimalFormat("0.000");
        txt_total_ttc_client.setText(decF.format(clientJFV.getTotalTTC()));


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
        return this.listClientJFV.get(listPosition).getListe_facture_parèclient().get(expandedListPosition);

     /*   return this.expandableListDetail.get(this.listClientRemise.get(listPosition))
                .get(expandedListPosition);*/
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {


        convertView = inflater.inflate(R.layout.item_facture_par_client, null);


        final FV_client_JFV fv_client_jfv = (FV_client_JFV) getChild(groupPosition, childPosition);


        TextView txt_num_piece = (TextView) convertView.findViewById(R.id.txt_num_piece);
        TextView txt_mnt_facture = (TextView) convertView.findViewById(R.id.txt_mnt_facture);

        TextView txt_date_piece = (TextView) convertView.findViewById(R.id.txt_date_piece);
        TextView txt_ht = (TextView) convertView.findViewById(R.id.txt_ht);

        TextView txt_tva = (TextView) convertView.findViewById(R.id.txt_tva);
        TextView txt_remise = (TextView) convertView.findViewById(R.id.txt_remise);

        TextView txt_t_fiscal = (TextView) convertView.findViewById(R.id.txt_t_fiscal);


        CardView btn_detail_facture = (CardView) convertView.findViewById(R.id.btn_detail_facture);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        txt_num_piece.setText(fv_client_jfv.getNumeroPiece());
        txt_date_piece.setText(sdf.format(fv_client_jfv.getDatePiece()));

        DecimalFormat decF = new DecimalFormat("0.000");
        txt_mnt_facture.setText(decF.format(fv_client_jfv.getTotalTTC()));
        txt_ht.setText(decF.format(fv_client_jfv.getTotalHT()));
        txt_tva.setText(decF.format(fv_client_jfv.getTotalTVA()));
        txt_remise.setText(decF.format(fv_client_jfv.getTotalRemiseg()));
        txt_t_fiscal.setText(decF.format(fv_client_jfv.getTimbreFiscal()));

        btn_detail_facture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FragmentManager fm = activity.getFragmentManager();
                DialogDetailFactureVente dialog = new  DialogDetailFactureVente( );
                dialog.setNumeroFacture(fv_client_jfv.getNumeroPiece());
                dialog.show(fm, "");



            }
        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {


        return this.listClientJFV.get(listPosition).getListe_facture_parèclient().size();

        /* return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)) .size();*/
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.listClientJFV.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listClientJFV.size();
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
package com.example.suivieadministratif.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;


import com.example.suivieadministratif.R;
import com.example.suivieadministratif.model.Article_BL_JAV;
import com.example.suivieadministratif.model.Bl_Client_JAV;
import com.example.suivieadministratif.model.Client;
import com.example.suivieadministratif.model.ClientJAV;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class JournalArticleVenteExtensibleAdapter extends BaseExpandableListAdapter {

    private Activity activity;
    private ArrayList<ClientJAV> listClientJAV;
    public LayoutInflater inflater;


    public JournalArticleVenteExtensibleAdapter(Activity activity, ArrayList<ClientJAV> listClientJAV) {
        this.activity = activity;
        this.listClientJAV = listClientJAV;
        inflater = activity.getLayoutInflater();
        Log.e("adapter", "RemiseExpandableListAdapter");
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.item_entete_jav, null);
        ClientJAV clientJAV = (ClientJAV) getGroup(groupPosition);
       // Log.e("getViewGroup", "" + clientJAV.toString());


        CardView item_gratuite_entete = (CardView) convertView.findViewById(R.id.item_gratuite_entete);


        TextView txt_raison = (TextView) convertView.findViewById(R.id.txt_raison_social);
        TextView txt_rad = (TextView) convertView.findViewById(R.id.txt_rad);
        TextView txt_responsable = (TextView) convertView.findViewById(R.id.txt_rep);
        TextView txt_total_ttc_client = (TextView) convertView.findViewById(R.id.txt_total_ttc_client);


        txt_raison.setText(clientJAV.getRaisonSociale());


        txt_rad.setText(clientJAV.getRAD());
        txt_responsable.setText(clientJAV.getREPRESENTANT() );

        DecimalFormat   decF  = new DecimalFormat("0.000")  ;
        txt_total_ttc_client.setText(decF.format(clientJAV.getTotalTTC()));



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
        return this.listClientJAV.get(listPosition).getList_bl_par_client().get(expandedListPosition);

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


        convertView = inflater.inflate(R.layout.item_bl_par_client, null);


        final Bl_Client_JAV bl_par_client = (Bl_Client_JAV) getChild(groupPosition, childPosition);


        TextView txt_num_bl = (TextView) convertView.findViewById(R.id.txt_num_bl);
        TextView txt_mnt_bl = (TextView) convertView.findViewById(R.id.txt_mnt_bl);
        ListView lv_list_detail_bl =  (ListView) convertView.findViewById(R.id.lv_list_detail_bl);



        int nbr_ligne = bl_par_client.getList_art_par_bl().size();
        int height = nbr_ligne * 80;


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);

        params.height = height;

        lv_list_detail_bl.setLayoutParams(params);

        txt_num_bl.setText( bl_par_client.getNumeroPiece());

        DecimalFormat  decF  = new DecimalFormat("0.000")  ;
        txt_mnt_bl.setText(decF.format(bl_par_client.getMontantTTC()));


        ///  adapter  ligne  bl


          Detail_BL_Adapter1 detail_bt_adapter1 = new Detail_BL_Adapter1(bl_par_client.getList_art_par_bl());
          lv_list_detail_bl.setAdapter(detail_bt_adapter1);


        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {


        return this.listClientJAV.get(listPosition).getList_bl_par_client().size();

        /* return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)) .size();*/
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.listClientJAV.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listClientJAV.size();
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
package com.example.suivieadministratif.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.model.LigneSuiviCMD_frns;
import com.example.suivieadministratif.model.SuiviCMD_frns;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BonAchatSuiviCMDRVAdapter extends RecyclerView.Adapter<BonAchatSuiviCMDRVAdapter.ViewHolder> {


    private Activity activity;
    private final ArrayList<SuiviCMD_frns> list_SuiviCMD_frns  ; //= new ArrayList<>();
    DecimalFormat numberFormat = new DecimalFormat("0.000");
    public static String login;
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    DateFormat heureFormat = new SimpleDateFormat("HH:mm");

    public BonAchatSuiviCMDRVAdapter(Activity activity, ArrayList<SuiviCMD_frns> list_SuiviCMD_frns) {

        this.activity = activity;
        this.list_SuiviCMD_frns = list_SuiviCMD_frns;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bon_commande_achat, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final SuiviCMD_frns  suiviCMD_frns = list_SuiviCMD_frns.get(position);

        holder.txt_num_ba.setText(suiviCMD_frns.getNumeroBonAchat());
        holder.txt_date_bon_achat.setText(sdf.format(suiviCMD_frns.getDateBonCommandeAchat()));
        holder.txt_code_frns.setText(suiviCMD_frns.getCodeFournisseur());

        holder.txt_raison_fournisseur.setText(suiviCMD_frns.getRaisonSocial());
        holder.txt_etablie_par.setText( "");
        holder.txt_tot_ht.setText(numberFormat.format (suiviCMD_frns.getTotalHT()));


        /*
      else    if (bonLivraisonVente.getNumeroEtatPaiment().equals("E08"))  // payé

            holder.item_bl.setCardBackgroundColor(activity.getResources().getColor(R.color.back_card_green));

        else if (bonLivraisonVente.getNumeroEtatPaiment().equals("E30")) // Partiellement Payé

            holder.item_bl.setCardBackgroundColor(activity.getResources().getColor(R.color.back_card_orange)) ;

         else if (!bonLivraisonVente.isDraft())
         {
             holder.ll_draft.setVisibility(View.GONE);
             holder.item_bl.setCardBackgroundColor(activity.getResources().getColor(R.color.white));
         }
        else

            holder.item_bl.setCardBackgroundColor(activity.getResources().getColor(R.color.white));

        */

        int nbr_ligne = suiviCMD_frns.getListLigneSuiviCMD_frns().size();
        int height = nbr_ligne * 80;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);

        params.height = height;
        holder.lv_list_detail_achat.setLayoutParams(params);

        Detail_BL_Adapter1 detail_bt_adapter1 = new Detail_BL_Adapter1(suiviCMD_frns.getListLigneSuiviCMD_frns());
        holder.lv_list_detail_achat.setAdapter(detail_bt_adapter1);

    }

    @Override
    public int getItemCount() {
        Log.e("size", "" + list_SuiviCMD_frns.size());
        return list_SuiviCMD_frns.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView   item_bon_commande_achat  ;
        public TextView txt_num_ba;
        public TextView txt_date_bon_achat;

        public TextView txt_code_frns;
        public TextView txt_raison_fournisseur;

        public CardView card_list_detail_achat;
        public ListView lv_list_detail_achat;

        public TextView txt_tot_ht ;
        public TextView txt_etablie_par;

        public ViewHolder(View itemView) {
            super(itemView);

            item_bon_commande_achat = (CardView) itemView.findViewById(R.id.item_bon_commande_achat);

            txt_num_ba = (TextView) itemView.findViewById(R.id.txt_num_ba);
            txt_date_bon_achat = (TextView) itemView.findViewById(R.id.txt_date_bon_achat);

            txt_code_frns = (TextView) itemView.findViewById(R.id.txt_code_frns);
            txt_raison_fournisseur = (TextView) itemView.findViewById(R.id.txt_raison_fournisseur);

            card_list_detail_achat  = (CardView) itemView.findViewById(R.id.card_list_detail_achat);
            lv_list_detail_achat    = (ListView) itemView.findViewById(R.id.lv_list_detail_achat);


            txt_etablie_par = (TextView) itemView.findViewById(R.id.txt_etablie_par);
            txt_tot_ht = (TextView)itemView.findViewById(R.id.txt_tot_ht);


        }

    }


    public class Detail_BL_Adapter1 extends ArrayAdapter<LigneSuiviCMD_frns> {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        private final ArrayList<LigneSuiviCMD_frns> list_ligneSuiviCMD_frns;

        public Detail_BL_Adapter1(ArrayList<LigneSuiviCMD_frns> list_ligneSuiviCMD_frns) {
            super(activity, R.layout.item_detail_bon_cmd_achat, list_ligneSuiviCMD_frns);

            this.list_ligneSuiviCMD_frns = list_ligneSuiviCMD_frns;

        }

        public View getView(int position, View view, ViewGroup parent) {

            LayoutInflater inflater = activity.getLayoutInflater();

            View rowView = inflater.inflate(R.layout.item_detail_bon_cmd_achat, null, true);

            LigneSuiviCMD_frns ligneSuiviCMD_frns = list_ligneSuiviCMD_frns.get(position);

            TextView txt_designation = (TextView) rowView.findViewById(R.id.txt_designation_article);
            TextView txt_qt_cmd      = (TextView) rowView.findViewById(R.id.txt_qt_cmd);
            TextView txt_qt_livre    = (TextView) rowView.findViewById(R.id.txt_qt_livre);


            txt_designation.setText(ligneSuiviCMD_frns.getDesignation());
            txt_qt_cmd.setText     (ligneSuiviCMD_frns.getQt_cmd() + "");
            txt_qt_livre.setText   (ligneSuiviCMD_frns.getQt_livre() + "");

            return rowView;

        }

    }


}



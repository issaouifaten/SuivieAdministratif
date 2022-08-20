package com.example.suivieadministratif.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.suivieadministratif.R;
import com.example.suivieadministratif.model.ImpayeFournisseur;
import com.example.suivieadministratif.model.ImpayeFournisseurEntete;
import com.example.suivieadministratif.model.PieceRecouvrementEtendu;
import com.example.suivieadministratif.model.TypePiece;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class RegClientEtenduELVAdapter extends BaseExpandableListAdapter {

    private Activity activity;
    private ArrayList<TypePiece> listTypePiece  ;
    public LayoutInflater inflater;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    public RegClientEtenduELVAdapter(Activity activity, ArrayList<TypePiece> listTypePiece) {

        this.activity = activity;
        this.listTypePiece = listTypePiece;
        inflater = activity.getLayoutInflater();
        Log.e("adapter", "RemiseExpandableListAdapter");

    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.item_entete_type_piece, null);
        TypePiece  typePiece   = (TypePiece) getGroup(groupPosition);

        CardView item_gratuite_entete = (CardView) convertView.findViewById(R.id.item_gratuite_entete);

        TextView txt_libelle = (TextView) convertView.findViewById(R.id.txt_libelle);
        TextView txt_total_par_type = (TextView) convertView.findViewById(R.id.txt_total_par_type);

        txt_libelle.setText(typePiece.getLibelle());


        final NumberFormat decF = NumberFormat.getNumberInstance(Locale.FRENCH);
        decF.setMinimumFractionDigits(3);
        decF.setMaximumFractionDigits(3);

        //txt_total_par_type.setText(decF.format(typePiece.get()));

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
        return this.listTypePiece.get(listPosition).getListPieceRecouvEtendu().get(expandedListPosition);

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


        rowView = inflater.inflate(R.layout.item_piece_client_etendu, null);

        TextView txt_num_piece = (TextView) rowView.findViewById(R.id.txt_num_piece);
        TextView txt_date_piece   = (TextView) rowView.findViewById(R.id.txt_date_piece);
        TextView txt_montant_piece  = (TextView) rowView.findViewById(R.id.txt_montant_piece);


        final PieceRecouvrementEtendu  pieceRecEtendu  = (PieceRecouvrementEtendu) getChild(groupPosition, childPosition);


            txt_num_piece.setText(pieceRecEtendu.getNumeroPiece());
            txt_date_piece.setText(sdf.format(pieceRecEtendu.getDatePiece()));

            final NumberFormat decF = NumberFormat.getNumberInstance(Locale.FRENCH);
            decF.setMinimumFractionDigits(3);
            decF.setMaximumFractionDigits(3);

            txt_montant_piece.setText(decF.format(pieceRecEtendu.getMontant()) );

        return rowView;
    }

    @Override
    public int getChildrenCount(int listPosition) {

        return this.listTypePiece.get(listPosition).getListPieceRecouvEtendu().size();

        /* return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)) .size();*/
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.listTypePiece.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listTypePiece.size();
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
package com.example.suivieadministratif.menu.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.suivieadministratif.R;
import com.example.suivieadministratif.ui.statistique_rapport_activite.StatAchatFragment;
import com.example.suivieadministratif.ui.statistique_rapport_activite.StatArticleFragment;
import com.example.suivieadministratif.ui.statistique_rapport_activite.StatCRMFragment;
import com.example.suivieadministratif.ui.statistique_rapport_activite.StatClientFragment;
import com.example.suivieadministratif.ui.statistique_rapport_activite.StatCompteFragment;
import com.example.suivieadministratif.ui.statistique_rapport_activite.StatFournisseurFragment;
import com.example.suivieadministratif.ui.statistique_rapport_activite.StatImportationFragment;
import com.example.suivieadministratif.ui.statistique_rapport_activite.StatSRMFragment;
import com.example.suivieadministratif.ui.statistique_rapport_activite.StatStockFragment;
import com.example.suivieadministratif.ui.statistique_rapport_activite.StatVenteFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{
            R.string.tab_text_1, R.string.tab_text_2,
            R.string.tab_text_3, R.string.tab_text_4,
            R.string.tab_text_5, R.string.tab_text_6,
            R.string.tab_text_7, R.string.tab_text_8,
            R.string.tab_text_9, R.string.tab_text_10};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if (position ==0)
        {
            return StatArticleFragment.newInstance(position);
        }
        else  if (position ==1)
        {
            return StatFournisseurFragment.newInstance(position);
        }
        else  if (position ==2)
        {
            return StatClientFragment.newInstance(position);
        }
        else  if (position ==3)
        {
            return StatStockFragment.newInstance(position);
        }
        else  if (position ==4)
        {
            return StatAchatFragment.newInstance(position);
        }
        else  if (position ==5)
        {
            return StatVenteFragment.newInstance(position);
        }
        else  if (position ==6)
        {
            return StatCompteFragment.newInstance(position);
        }
        else  if (position ==7)
        {
            return StatCRMFragment.newInstance(position);
        }
        else  if (position ==8)
        {
            return StatImportationFragment.newInstance(position);
        }
        else  if (position ==9)
        {
            return StatSRMFragment.newInstance(position);
        }
        else
        return PlaceholderFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 10 ;
    }
}
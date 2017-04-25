package com.example.raych.nhu;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by raych on 4/22/2017.
 */

public class Store_TabAdapter  extends FragmentPagerAdapter implements Store_Popular.OnFragmentInteractionListener,
Store_Top_Paid.OnFragmentInteractionListener,Store_Top_Free.OnFragmentInteractionListener{

    final int tab_count =3;  //number of tabs in Store Activity
    private String tab_titles[] = new String[] {"Fresh","Top Free","Top Paid"};

    public Store_TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Store_Popular frag0 = new Store_Popular();
                return frag0;
            case 1:
                Store_Top_Free frag1 = new Store_Top_Free();
                return frag1;
            case 2:
                Store_Top_Paid frag2 = new Store_Top_Paid();
                return frag2;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    //Return title of tab.
    @Override
    public CharSequence getPageTitle(int position) {
        return tab_titles[position];
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}

package iak.intermediate.hydrargyrum.ourmoviebook.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import iak.intermediate.hydrargyrum.ourmoviebook.activity.TabMyFavorite;
import iak.intermediate.hydrargyrum.ourmoviebook.activity.TabPopularMovies;

/**
 * Created by hydrargyrum on 2/25/2018.
 */

public class MoviePagerAdapter extends FragmentStatePagerAdapter{

    // int to count number of tabs
    int tabCount;

    // Constructor of class
    public MoviePagerAdapter(FragmentManager _fm, int _tabCount){
        super(_fm);

        // init tab count
        this.tabCount = _tabCount;
    }

    // Override method getItem

    @Override
    public Fragment getItem(int position) {

        // Returning current tabs
        switch(position){
            case 0:
                TabPopularMovies tabPopularMovies = new TabPopularMovies();
                return tabPopularMovies;
            case 1:
                TabMyFavorite tabMyFavorite = new TabMyFavorite();
                return tabMyFavorite;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

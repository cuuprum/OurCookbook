package iak.intermediate.hydrargyrum.ourmoviebook.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import iak.intermediate.hydrargyrum.ourmoviebook.R;

/**
 * Created by hydrargyrum on 2/25/2018.
 * Class Tab 1 : Popular Movies
 */

// This class extends fragment
public class TabPopularMovies extends Fragment{

    // Override method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Returning the layout file after inflating
        // change R.layout.tab_popular_movie
        return inflater.inflate(R.layout.tab_popular_movie, container, false);
    }
}

package iak.intermediate.hydrargyrum.ourmoviebook.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import iak.intermediate.hydrargyrum.ourmoviebook.R;

/**
 * Created by hydrargyrum on 2/25/2018.
 * Tab 2 : My Favorite
 */

public class TabMyFavorite extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.tab_my_favorite, container, false);
    }
}

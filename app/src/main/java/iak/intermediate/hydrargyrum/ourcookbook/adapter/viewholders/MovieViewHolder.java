package iak.intermediate.hydrargyrum.ourcookbook.adapter.viewholders;

/**
 * Created by hydrargyrum on 2/23/2018.
 */

import android.widget.ImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import iak.intermediate.hydrargyrum.ourcookbook.R;

public class MovieViewHolder extends RecyclerView.ViewHolder {

    public ImageView gambar_movie;
    public LinearLayout layout_parent;

    public MovieViewHolder(View _itemView){
        super(_itemView);
        gambar_movie = (ImageView) _itemView.findViewById(R.id.gambar_movie);
        layout_parent = (LinearLayout) _itemView.findViewById(R.id.layout_parent);
    }

    public LinearLayout getItem(){
        return layout_parent;
    }
}

package iak.intermediate.hydrargyrum.ourmoviebook.adapter.viewholders;

/**
 * Created by hydrargyrum on 2/23/2018.
 */

import android.widget.ImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import iak.intermediate.hydrargyrum.ourmoviebook.R;

public class MovieViewHolder extends RecyclerView.ViewHolder {

    public ImageView gambar_movie;
    public RelativeLayout layout_parent;
    public TextView tvTitle;

    public MovieViewHolder(View _itemView){
        super(_itemView);
        gambar_movie = (ImageView) _itemView.findViewById(R.id.gambar_movie);
        tvTitle = (TextView) _itemView.findViewById(R.id.tv_title);
        layout_parent = (RelativeLayout) _itemView.findViewById(R.id.layout_parent);
    }

    public RelativeLayout getItem(){
        return layout_parent;
    }
}

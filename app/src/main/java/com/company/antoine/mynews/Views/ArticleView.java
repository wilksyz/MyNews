package com.company.antoine.mynews.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.antoine.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ArticleView extends RecyclerView.ViewHolder{

    @BindView(R.id.recycler_image_view) ImageView imageView;
    @BindView(R.id.recycler_text_view_date) TextView dateView;
    @BindView(R.id.recycler_text_view_section) TextView sectionView;
    @BindView(R.id.recycler_text_view_title) TextView tittleView;

    public ArticleView(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


}

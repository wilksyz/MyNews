package com.company.antoine.mynews.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.company.antoine.mynews.Models.Result;
import com.company.antoine.mynews.R;

import java.util.List;



public class RecyclerViewAdapter extends RecyclerView.Adapter<ArticleViewHolder> {

    private List<Result> articleList;
    private RequestManager glide;
    private int position;

    public RecyclerViewAdapter(List<Result> article, RequestManager pGlide, int pPosition) {
        articleList = article;
        glide = pGlide;
        position = pPosition;
    }


    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_recycler, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder articleViewHolder, int i) {
            articleViewHolder.updateArticleListView(articleList.get(i), glide, position);
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public Result getArticle(int position){
        return articleList.get(position);
    }
}

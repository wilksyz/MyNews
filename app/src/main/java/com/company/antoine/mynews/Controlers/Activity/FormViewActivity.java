package com.company.antoine.mynews.Controlers.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.company.antoine.mynews.Models.Result;
import com.company.antoine.mynews.Models.TimesArticleAPI;
import com.company.antoine.mynews.R;
import com.company.antoine.mynews.Utils.ItemClickSupport;
import com.company.antoine.mynews.Views.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public abstract class FormViewActivity extends AppCompatActivity {

    protected List<Result> article;
    protected RecyclerViewAdapter adapter;
    @BindView(R.id.recycler_view_search) RecyclerView recyclerView;
    protected CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_search);
        ButterKnife.bind(this);
        configureOnClickRecyclerView();

    }

    protected void configureRecycler(int position){
        this.article = new ArrayList<>();
        this.adapter = new RecyclerViewAdapter(this.article, Glide.with(this), position);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.view_recycler)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Result objectArticle = adapter.getArticle(position);
                        String url = objectArticle.getUrl();
                        Intent intent = new Intent(FormViewActivity.this, WebViewActivity.class);
                        intent.putExtra("url", url);
                        startActivity(intent);
                    }
                });

    }

    protected void updateUI(TimesArticleAPI blockArticle){

        article.clear();
        if (blockArticle.getResults() != null){
            article.addAll(blockArticle.getResults());
        }else if (blockArticle.getResponse().getDocs().size() != 0){
            article.addAll(blockArticle.getResponse().getDocs());
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Your research")
                    .setMessage("No result")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .create()
                    .show();
        }
        adapter.notifyDataSetChanged();
    }

    protected DisposableObserver<TimesArticleAPI> getDisposable(){
        return new DisposableObserver<TimesArticleAPI>() {
            @Override
            public void onNext(TimesArticleAPI mostPopular) {
                updateUI(mostPopular);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG","On Error",e);
            }

            @Override
            public void onComplete() {
                Log.i("TAG","On Complete !!");
            }
        };
    }
}

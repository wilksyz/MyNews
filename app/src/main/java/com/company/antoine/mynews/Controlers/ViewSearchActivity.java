package com.company.antoine.mynews.Controlers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.company.antoine.mynews.Models.Result;
import com.company.antoine.mynews.Models.TimesArticleAPI;
import com.company.antoine.mynews.R;
import com.company.antoine.mynews.Utils.ItemClickSupport;
import com.company.antoine.mynews.Utils.NYTimesStreams;
import com.company.antoine.mynews.Views.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class ViewSearchActivity extends AppCompatActivity {

    private List<Result> article;
    CompositeDisposable disposable = new CompositeDisposable();
    private RecyclerViewAdapter adapter;
    @BindView(R.id.recycler_view_search) RecyclerView recyclerView;
    Map<String,String> queryData = new HashMap<>();
    private String mBeginDate;
    private String mEndDate;
    private String mQueryTerm;
    private String mSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_search);
        ButterKnife.bind(this);
        configureRecycler();
        configureOnClickRecyclerView();
        getSettingsURL();
        executeHttpRequestArticleSearch();
    }

    private void configureRecycler(){
        this.article = new ArrayList<>();
        this.adapter = new RecyclerViewAdapter(this.article, Glide.with(this), 2);
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
                        Intent intent = new Intent(ViewSearchActivity.this, WebViewActivity.class);
                        intent.putExtra("url", url);
                        startActivity(intent);
                    }
                });

    }

    private void getSettingsURL(){
        mBeginDate = getIntent().getStringExtra("beginDate");
        mEndDate = getIntent().getStringExtra("endDate");
        mQueryTerm = getIntent().getStringExtra("queryTerm");
        mSection = getIntent().getStringExtra("sectionChecked");

        if (mBeginDate != null){
            queryData.put("begin_date", mBeginDate);
        }
        if (mEndDate != null){
            queryData.put("end_date", mEndDate);
        }
        queryData.put("q", mQueryTerm);
        queryData.put("fq", mSection);
    }

    private void executeHttpRequestArticleSearch(){
        disposable.add((Disposable) NYTimesStreams.streamFetchTimesArticleSearch(queryData).subscribeWith(getDisposable()));
    }

    private DisposableObserver<TimesArticleAPI> getDisposable(){
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
                Log.e("TAG","On Complete search!!");
            }
        };
    }

    private void updateUI(TimesArticleAPI blockArticle){
        article.clear();
        article.addAll(blockArticle.getResponse().getDocs());
        adapter.notifyDataSetChanged();
    }
}

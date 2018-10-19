package com.company.antoine.mynews.Controlers.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.company.antoine.mynews.Controlers.Activity.WebViewActivity;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerFragment extends Fragment {

    private static final String KEY_POSITION="position";
    CompositeDisposable disposables = new CompositeDisposable();
    private List<Result> article;
    private RecyclerViewAdapter adapter;
    Map<String,String> queryData = new HashMap<>();
    @BindView(R.id.fragment_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.fragment_refresh_view) SwipeRefreshLayout swipeRefreshLayout;

    public ViewPagerFragment() { }

    public static ViewPagerFragment newInstance(int position){

        ViewPagerFragment view = new ViewPagerFragment();

        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        view.setArguments(args);
        return view;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_view_pager, container, false);
        ButterKnife.bind(this, result);
        queryData.put("fq","news_desk:(\"Business\")");

        configureRecycler(getArguments().getInt(KEY_POSITION, -1));
        configureRefreshLayout(getArguments().getInt(KEY_POSITION, -1));
        configureOnClickRecyclerView();
        swipeRefreshLayout.setRefreshing(true);
        executeStream(getArguments().getInt(KEY_POSITION, -1));

        return result;
    }

    private void configureRecycler(int position){
        this.article = new ArrayList<>();
        this.adapter = new RecyclerViewAdapter(this.article, Glide.with(this), position);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void configureRefreshLayout(final int position){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                executeStream(position);
            }
        });
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.view_recycler)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Result objectArticle = adapter.getArticle(position);
                        String url = objectArticle.getUrl();
                        Intent intent = new Intent(getActivity(), WebViewActivity.class);
                        intent.putExtra("url", url);
                        startActivity(intent);
                    }
                });

    }

    private void executeStream(int position){
        switch (position){
            case 0: executeHttpRequestTopStories();
            break;
            case 1: executeHttpRequestMostPopular();
            break;
            case 2 : executeHttpRequestArticleSearch();
            break;
            default: break;
        }
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
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onComplete() {
                Log.e("TAG","On Complete !!");
            }
        };
    }

    private void executeHttpRequestMostPopular(){
        disposables.add( NYTimesStreams.streamFetchTimesMostPopular("Movies").subscribeWith(getDisposable()));
    }

    private void executeHttpRequestTopStories(){
        disposables.add( NYTimesStreams.streamFetchTimesTopStories("world").subscribeWith(getDisposable()));
    }

    private void executeHttpRequestArticleSearch(){
        disposables.add((Disposable) NYTimesStreams.streamFetchTimesArticleSearch(queryData).subscribeWith(getDisposable()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposeOnDestroy();
    }

    private void disposeOnDestroy(){
        if (this.disposables != null && !this.disposables.isDisposed()) this.disposables.dispose();
    }

    private void updateUI(TimesArticleAPI blockArticle){
        swipeRefreshLayout.setRefreshing(false);
        article.clear();
        if (blockArticle.getResults() != null){
            article.addAll(blockArticle.getResults());
        }else if (blockArticle.getResponse() != null){
            article.addAll(blockArticle.getResponse().getDocs());
        }
        adapter.notifyDataSetChanged();
    }
}

package com.company.antoine.mynews.Fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.company.antoine.mynews.Models.Response;
import com.company.antoine.mynews.Models.Result;
import com.company.antoine.mynews.Models.TimesArticleAPI;
import com.company.antoine.mynews.R;
import com.company.antoine.mynews.Utils.ItemClickSupport;
import com.company.antoine.mynews.Utils.NYTimesStreams;
import com.company.antoine.mynews.Views.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerFragment extends Fragment {

    private static final String KEY_POSITION="position";
    CompositeDisposable disposables = new CompositeDisposable();
    private List<Result> article;
    private RecyclerViewAdapter adapter;
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
        int position = getArguments().getInt(KEY_POSITION, -1);

        configureRecycler(position);
        configureRefreshLayout(position);
        configureOnClickRecyclerView();

        if (position == 0){
            executeHttpRequestTopStories();
        }
        if (position == 1){
            executeHttpRequestMostPopular();
        }
        if (position == 2){
            executeHttpRequestArticleSearch();
        }


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
                if (position == 0){
                    executeHttpRequestTopStories();
                }else if (position == 1){
                    executeHttpRequestMostPopular();
                }else{
                    executeHttpRequestArticleSearch();
                }
            }
        });
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.view_recycler)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Result objectArticle = adapter.getArticle(position);
                        //Toast.makeText(getContext(), "You clicked on user : "+objectArticle.getUrl(), Toast.LENGTH_SHORT).show();
                        String url = objectArticle.getUrl();
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    }
                });

    }

    private void executeHttpRequestMostPopular(){
        disposables.add( NYTimesStreams.streamFetchTimesMostPopular("Movies").subscribeWith(new DisposableObserver<TimesArticleAPI>() {
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
                Log.e("TAG","On Complete !!");
            }
        }));
    }

    private void executeHttpRequestTopStories(){
        disposables.add( NYTimesStreams.streamFetchTimesTopStories("world").subscribeWith(new DisposableObserver<TimesArticleAPI>() {
            @Override
            public void onNext(TimesArticleAPI topStoriesAPI) {
                updateUI(topStoriesAPI);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG","On Error Top "+Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.e("TAG","On Complete top!!");
            }
        }));
    }

    private void executeHttpRequestArticleSearch(){
        disposables.add( NYTimesStreams.streamFetchTimesArticleSearch("news_desk:Business").subscribeWith(new DisposableObserver<TimesArticleAPI>() {
            @Override
            public void onNext(TimesArticleAPI articleSearchAPI) {
                articleSearchUpdateUI(articleSearchAPI.getResponse());
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG","On Error "+Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.e("TAG","On Complete search!!");
            }
        }));
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
        article.addAll(blockArticle.getResults());
        adapter.notifyDataSetChanged();
    }

    private void articleSearchUpdateUI(Response blockArticle){
        swipeRefreshLayout.setRefreshing(false);
        article.clear();
        article.addAll(blockArticle.getDocs());
        adapter.notifyDataSetChanged();
    }
}

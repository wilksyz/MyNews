package com.company.antoine.mynews.Controlers.Activity;

import android.os.Bundle;

import com.company.antoine.mynews.Utils.NYTimesStreams;

import io.reactivex.disposables.Disposable;

public class DrawerViewArticleActivity extends FormViewActivity {

    private String mSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSection = getIntent().getStringExtra("sectionChecked");
        executeHttpRequestTopStories();
        configureRecycler(0);
    }

    private void executeHttpRequestTopStories(){
        disposable.add((Disposable) NYTimesStreams.streamFetchTimesTopStories(mSection).subscribeWith(getDisposable()));
    }
}

package com.company.antoine.mynews.Controlers.Activity;

import android.os.Bundle;
import com.company.antoine.mynews.Utils.NYTimesStreams;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

public class ViewSearchActivity extends FormViewActivity {



    Map<String,String> queryData = new HashMap<>();
    private String mBeginDate;
    private String mEndDate;
    private String mQueryTerm;
    private String mSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSettingsURL();
        executeHttpRequestArticleSearch();
        configureRecycler(2);
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




}

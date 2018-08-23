package com.company.antoine.mynews.Controlers;


import android.os.Bundle;
import android.view.View;

public class NotificationActivity extends FormActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureNotificationPage();

    }

    private void configureNotificationPage(){
        mButtonEndDate.setVisibility(View.GONE);
        mButtonBeginDate.setVisibility(View.GONE);
        mTextViewEndDate.setVisibility(View.GONE);
        mTextViewBeginDate.setVisibility(View.GONE);
        mButtonSearch.setVisibility(View.GONE);
    }
}

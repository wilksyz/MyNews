package com.company.antoine.mynews.Controlers.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.company.antoine.mynews.Controlers.Fragment.DatePickerFragment;


public class SearchArticles extends FormActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureSearchPage();

        mButtonBeginDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
                mButtonClick = 1;
            }
        });

        mButtonEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
                mButtonClick = 2;
            }
        });

        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveSettings();
                mValidator.validate();
                if (mCheckCheckBox && mCheckEditText){
                    Intent intent = new Intent(SearchArticles.this,ViewSearchActivity.class);
                    intent.putExtra("sectionChecked", mSection);
                    intent.putExtra("queryTerm", mQueryTerm);
                    intent.putExtra("beginDate", mBeginDate);
                    intent.putExtra("endDate", mEndDate);
                    startActivity(intent);
                }
            }
        });
    }

    private void configureSearchPage(){
        mSwitchButtonNotification.setVisibility(View.GONE);
        mView.setVisibility(View.GONE);
    }

    private void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }
}

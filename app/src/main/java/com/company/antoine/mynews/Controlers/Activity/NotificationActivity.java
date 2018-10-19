package com.company.antoine.mynews.Controlers.Activity;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NotificationActivity extends FormActivity {

    private boolean mNotification;
    private String SAVE_TERM = "save term";
    private String SAVE_SECTION = "save section";
    private String SAVE_BUTTON = "save button";
    private String SAVE_BUTTON_CHECK = "save button check";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureNotificationPage();
        String mQueryTerm = getSharedPreferences("My settings", MODE_PRIVATE).getString(SAVE_TERM, null);
        boolean mActionNotification = getSharedPreferences("My settings", MODE_PRIVATE).getBoolean(SAVE_BUTTON, false);
        int mCheckBoxChecked = getSharedPreferences("My settings", MODE_PRIVATE).getInt(SAVE_BUTTON_CHECK, 0);
        if (mActionNotification){
            mEditTextSearchTerm.setText(mQueryTerm);
            checkBoxChecked(mCheckBoxChecked);
            mSwitchButtonNotification.setChecked(true);
        }

        mSwitchButtonNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    retrieveSettings();
                    mValidator.validate();
                    if (mCheckCheckBox && mCheckEditText){
                        mNotification = true;
                        onSave();
                    }else{
                        mSwitchButtonNotification.setChecked(false);
                    }
                } else {
                    // The toggle is disabled
                    mNotification = false;

                }
            }
        });
    }

    private void configureNotificationPage(){
        mButtonEndDate.setVisibility(View.GONE);
        mButtonBeginDate.setVisibility(View.GONE);
        mTextViewEndDate.setVisibility(View.GONE);
        mTextViewBeginDate.setVisibility(View.GONE);
        mButtonSearch.setVisibility(View.GONE);
    }

    private void onSave(){
        SharedPreferences.Editor saveSettings = getSharedPreferences("My settings", MODE_PRIVATE).edit();
        saveSettings.putString(SAVE_TERM, mQueryTerm);
        saveSettings.putString(SAVE_SECTION, mSection);
        saveSettings.putBoolean(SAVE_BUTTON, mNotification);
        saveSettings.putInt(SAVE_BUTTON_CHECK, mCheckBoxChecked);
        saveSettings.apply();
    }

    private void checkBoxChecked(int caseCheck){
        if (caseCheck == 0){
            mCheckBoxArts.setChecked(true);
        }else if (caseCheck == 1){
            mCheckBoxBusiness.setChecked(true);
        }else if (caseCheck == 2){
            mCheckBoxEntrepreneurs.setChecked(true);
        }else if (caseCheck == 3){
            mCheckBoxPolitics.setChecked(true);
        }else if (caseCheck == 4){
            mCheckBoxSports.setChecked(true);
        }else if (caseCheck == 5){
            mCheckBoxTravel.setChecked(true);
        }
    }

    @Override
    protected void onStop() {
        SharedPreferences.Editor saveSettings = getSharedPreferences("My settings", MODE_PRIVATE).edit();
        saveSettings.putBoolean(SAVE_BUTTON, mNotification);
        saveSettings.apply();
        super.onStop();
    }
}

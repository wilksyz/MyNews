package com.company.antoine.mynews.Controlers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.company.antoine.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends AppCompatActivity {

    @BindView(R.id.editText_search_term) EditText mEditTextSearchTerm;
    @BindView(R.id.button_picker_end_date) Button mButtonEndDate;
    @BindView(R.id.button_picker_begin_date) Button mButtonBeginDate;
    @BindView(R.id.textView_end_date) TextView mTextViewEndDate;
    @BindView(R.id.textView_begin_date) TextView mTextViewBeginDate;
    @BindView(R.id.checkBox_arts) CheckBox mCheckBoxArts;
    @BindView(R.id.checkBox_business) CheckBox mCheckBoxBusiness;
    @BindView(R.id.checkBox_entrepreneurs) CheckBox mCheckBoxEntrepreneurs;
    @BindView(R.id.checkBox_politics) CheckBox mCheckBoxPolitics;
    @BindView(R.id.checkBox_sports) CheckBox mCheckBoxSports;
    @BindView(R.id.checkBox_travel) CheckBox mCheckBoxTravel;
    @BindView(R.id.button_search) Button mButtonSearch;
    @BindView(R.id.switch_button_notification) Switch mSwitchButtonNotification;
    @BindView(R.id.view) View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.articles_search);
        ButterKnife.bind(this);
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

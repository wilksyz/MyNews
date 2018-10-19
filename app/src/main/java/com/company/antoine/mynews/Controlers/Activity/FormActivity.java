package com.company.antoine.mynews.Controlers.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.company.antoine.mynews.R;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class FormActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, Validator.ValidationListener{

    @BindView(R.id.editText_search_term) @NotEmpty(message = "search query term required") EditText mEditTextSearchTerm;
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
    protected String mBeginDate;
    protected String mEndDate;
    protected int mButtonClick;
    protected String mQueryTerm;
    protected String mSection = "news_desk:";
    protected Validator mValidator;
    protected boolean mCheckCheckBox;
    protected boolean mCheckEditText;
    protected int mCheckBoxChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        ButterKnife.bind(this);

        mValidator = new Validator(this);
        mValidator.setValidationListener(this);

    }

    protected void retrieveSettings(){
        mQueryTerm = mEditTextSearchTerm.getText().toString();
        if (mCheckBoxArts.isChecked()){
            mCheckCheckBox = true;
            mSection = mSection+" Arts";
            mCheckBoxChecked = 0;
        }else if(mCheckBoxBusiness.isChecked()){
            mCheckCheckBox = true;
            mSection = mSection+" Business Day";
            mCheckBoxChecked = 1;
        }else if(mCheckBoxEntrepreneurs.isChecked()){
            mCheckCheckBox = true;
            mSection = mSection+" Entrepreneurs";
            mCheckBoxChecked = 2;
        }else if(mCheckBoxPolitics.isChecked()){
            mCheckCheckBox = true;
            mSection = mSection+" Politics";
            mCheckBoxChecked = 3;
        }else if(mCheckBoxSports.isChecked()){
            mCheckCheckBox = true;
            mSection = mSection+" Sports";
            mCheckBoxChecked = 4;
        }else if(mCheckBoxTravel.isChecked()){
            mCheckCheckBox = true;
            mSection = mSection+" Travel";
            mCheckBoxChecked = 5;
        }else{
            mCheckCheckBox = false;
            Toast.makeText(getBaseContext(), "Choose a section!!!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onValidationSucceeded() {
        mCheckEditText = true;
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            mCheckEditText = false;

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        String mButtonDate;
        String date;
        String dayFormat;
        month++;
        if (day <= 9){
            dayFormat = ""+0+day;
        }else{
            dayFormat = ""+day;
        }
        if (month <= 9){
            String monthFormat = ""+0+month;
            mButtonDate = dayFormat+"/"+monthFormat+"/"+year;
            date = ""+year+monthFormat+day;
        }else{
            mButtonDate = dayFormat+"/"+month+"/"+year;
            date = ""+year+month+day;
        }
        switch (mButtonClick){
            case 1:
                mBeginDate = date;
                Log.e("TAG","Date:  "+date);
                mButtonBeginDate.setText(mButtonDate);
                break;
            case 2:
                mEndDate = date;
                mButtonEndDate.setText(mButtonDate);
                break;
            default:
                break;
        }
        mButtonClick = 0;
    }
}


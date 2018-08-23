package com.company.antoine.mynews.Controlers;

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
    @BindView(R.id.checkBox_arts)@Checked(message = "Choose a section") CheckBox mCheckBoxArts;
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
    protected boolean mCheck;

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
            mCheck = true;
            mSection = mSection+" Arts";
        }
        if (mCheckBoxBusiness.isChecked()){
            mCheck = true;
            mSection = mSection+" Business Day";
        }
        if (mCheckBoxEntrepreneurs.isChecked()){
            mCheck = true;
            mSection = mSection+" Entrepreneurs";
        }
        if (mCheckBoxPolitics.isChecked()){
            mCheck = true;
            mSection = mSection+" Politics";
        }
        if (mCheckBoxSports.isChecked()){
            mCheck = true;
            mSection = mSection+" Sports";
        }
        if (mCheckBoxTravel.isChecked()){
            mCheck = true;
            mSection = mSection+" Travel";
        }
    }

    @Override
    public void onValidationSucceeded() {
        Intent intent = new Intent(FormActivity.this,ViewSearchActivity.class);
        intent.putExtra("sectionChecked", mSection);
        intent.putExtra("queryTerm", mQueryTerm);
        intent.putExtra("beginDate", mBeginDate);
        intent.putExtra("endDate", mEndDate);
        startActivity(intent);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Log.e("TAG","picket"+day+month+year);
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


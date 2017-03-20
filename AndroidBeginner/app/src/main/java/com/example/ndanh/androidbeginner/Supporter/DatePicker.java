package com.example.ndanh.androidbeginner.Supporter;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ndanh on 3/6/2017.
 */

public abstract class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    OnDataPass dataPasser;

    public void DatePicker(){

    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        Date date = new Date(year,month,dayOfMonth);
        dataPasser.OnDataPass(date);
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int date = c.get(Calendar.DATE);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        // Create a new instance of TimePickerDialog and return it
        return new DatePickerDialog (getActivity(), this, year , month, date);
    }

    public interface OnDataPass {
        public void OnDataPass (Date date);
    }
}

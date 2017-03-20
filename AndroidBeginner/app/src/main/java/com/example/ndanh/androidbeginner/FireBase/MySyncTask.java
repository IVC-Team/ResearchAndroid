package com.example.ndanh.androidbeginner.FireBase;

import android.os.AsyncTask;

import com.example.ndanh.androidbeginner.Model.User;

import java.util.ArrayList;

/**
 * Created by ndanh on 3/17/2017.
 */

public class MySyncTask extends AsyncTask<User, String, ArrayList<User>> {
    @Override
    protected ArrayList<User> doInBackground(User... params) {

        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }
}

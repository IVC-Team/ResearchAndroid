package com.example.ndanh.androidbeginner.FireBase;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ndanh on 3/17/2017.
 */

public abstract class FireBaseHelper<T> {

    public FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    protected abstract List<T> getAllData();

    protected abstract boolean insertData(T tClass);

    protected abstract boolean updateData(T tClass);

    protected abstract int deleteData(T tClass);
}

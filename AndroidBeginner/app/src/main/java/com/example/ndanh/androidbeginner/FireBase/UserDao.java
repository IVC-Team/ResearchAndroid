package com.example.ndanh.androidbeginner.FireBase;

import android.os.Handler;

import com.example.ndanh.androidbeginner.Model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by ndanh on 3/17/2017.
 */

public class UserDao extends FireBaseHelper<User> {

    private List<User> users = new ArrayList<User>();
    private Handler myHandler = new Handler();
    private AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    @Override
    public List<User> getAllData() {
        Thread threadLogin = new Thread(new Runnable() {
            @Override
            public void run() {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("users");

                myRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        threadLogin.start();
        return users;
    }

    @Override
    protected boolean insertData(User tClass) {
        return false;
    }

    @Override
    protected boolean updateData(User tClass) {
        return false;
    }

    @Override
    protected int deleteData(User tClass) {
        return 0;
    }

    private void GetUsers(){

    }

}

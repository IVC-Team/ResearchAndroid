package com.example.ndanh.androidbeginner.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ndanh.androidbeginner.Model.User;
import com.example.ndanh.androidbeginner.R;
import com.example.ndanh.androidbeginner.Supporter.EXTRAKEY;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;



public class MyFragment extends Fragment {
    private TextView textViewName,textViewEmail,textViewDescirbe;
    private Context context;
    private String idUser;

    public MyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        idUser = getArguments().getString( EXTRAKEY.EXTRA_STRING_CURRENT_USER_ID);
        View view= inflater.inflate(R.layout.fragment_my, container, false);
        textViewName = (TextView) view.findViewById(R.id.name_info);
        textViewEmail = (TextView) view.findViewById(R.id.email_info);
        textViewDescirbe = (TextView) view.findViewById(R.id.describe_info);
        loadData();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void loadData(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
             getUserInfo();
            }
        });
        thread.start();
    }

    private void getUserInfo() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        myRef.orderByChild("id").equalTo(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    User user = child.getValue(User.class);
                    textViewName.setText(user.name);
                    textViewEmail.setText(user.email);
                    textViewDescirbe.setText(user.describe);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}

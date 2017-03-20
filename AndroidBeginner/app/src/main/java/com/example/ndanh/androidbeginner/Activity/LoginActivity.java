package com.example.ndanh.androidbeginner.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ndanh.androidbeginner.Base.BaseActivity;
import com.example.ndanh.androidbeginner.Model.User;
import com.example.ndanh.androidbeginner.R;
import com.example.ndanh.androidbeginner.Supporter.AuthenticateHash;
import com.example.ndanh.androidbeginner.Supporter.EXTRAKEY;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {

    private Handler myHandler = new Handler();
    private EditText txtPassWord;
    private AutoCompleteTextView txtEmail;
    private Button buttonLogin, btnExit;
    private static final String TAG = "LoginActivity";
    private ProgressBar progressBar;
    private AtomicBoolean atomicBoolean = new AtomicBoolean(false);
    private User  user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtPassWord = (EditText) findViewById(R.id.password);
        txtEmail = (AutoCompleteTextView) findViewById(R.id.email);
        buttonLogin =(Button) findViewById(R.id.email_sign_in_button);
        btnExit =(Button) findViewById(R.id.exit_button);
        buttonLogin.setOnClickListener(btnLoginListener);
        btnExit.setOnClickListener(btnExitListener);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);
    }

    private void login(){
        atomicBoolean.set(true);
        OnLoginProgress(atomicBoolean.get());
        Thread threadLogin = new Thread(new Runnable() {
            @Override
            public void run() {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("users");
                myRef.orderByChild("email").equalTo(txtEmail.getText().toString().trim()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean isValid = false;
                        for (DataSnapshot chilDataSnapshot : dataSnapshot.getChildren()) {
                            user = chilDataSnapshot.getValue(User.class);

                            if (user.password.equals(AuthenticateHash.Password(txtPassWord.getText().toString()))) {
                                isValid = true;
                                break;
                            }
                        }
                        if(isValid){
                            myHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    atomicBoolean.set(false);
                                    OnLoginProgress(atomicBoolean.get());
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra(EXTRAKEY.EXTRA_STRING_CURRENT_USER_ID ,user.id);
                                    startActivity(intent);
                                    return;
                                }
                            });
                        }else{
                            atomicBoolean.set(false);
                            OnLoginProgress(atomicBoolean.get());
                            Toast.makeText(getApplicationContext(),"Login failed. Please contact admin for more details.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        atomicBoolean.set(false);
                        OnLoginProgress(atomicBoolean.get());
                        Toast.makeText(getApplicationContext(),"Login failed. Please contact admin for more details.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        if(txtEmail.getText().toString().trim().equals("") ||txtPassWord.getText().toString().equals("")){
            atomicBoolean.set(false);
            OnLoginProgress(atomicBoolean.get());
            Toast.makeText(getApplicationContext(),"Login failed. Email and password cant be blank.", Toast.LENGTH_SHORT).show();
        }else {
            threadLogin.start();
        }

    }

    private View.OnClickListener btnLoginListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            login();
        }
    };

    private View.OnClickListener btnExitListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnClickListener btnCancelListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            atomicBoolean.set(false);
            OnLoginProgress(atomicBoolean.get());
        }
    };

    private void OnLoginProgress(boolean state){
        txtPassWord.setEnabled(!state);
        txtEmail.setEnabled(!state);
        buttonLogin.setEnabled(!state);
        if(!state){
            progressBar.setVisibility(View.GONE);
            btnExit.setText(R.string.action_exit);
            btnExit.setOnClickListener(btnExitListener);
        }else {
            progressBar.setVisibility(View.VISIBLE);
            btnExit.setText(R.string.action_cancel);
            btnExit.setOnClickListener(btnCancelListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        txtPassWord.clearFocus();
        txtPassWord.setText("");
    }

}


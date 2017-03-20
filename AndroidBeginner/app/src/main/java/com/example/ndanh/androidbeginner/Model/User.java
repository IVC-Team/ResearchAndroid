package com.example.ndanh.androidbeginner.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by ndanh on 3/2/2017.
 */

public class User {

    public String describe;
    public String email;
    public String id;
    public String name;
    public String password;


    public User(String email, String name,String describe, String password) {
        this.describe = describe;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public User() {
    }

}

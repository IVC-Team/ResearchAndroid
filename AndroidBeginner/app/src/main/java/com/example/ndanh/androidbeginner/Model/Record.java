package com.example.ndanh.androidbeginner.Model;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ndanh on 3/6/2017.
 */

public class Record {
    public String date;
    public String endtime;
    public String starttime;
    public String meetingroom;
    public String user;

    public Record(String date, String starttime, String endtime, String meetingroom, String user){
        this.date = date;
        this.starttime = starttime;
        this.endtime = endtime;
        this.meetingroom = meetingroom;
        this.user = user;
    }
    public Record(){

    }

    @Override
    public String toString() {
        return  this.date + this.starttime + this.endtime + this.meetingroom + this.user ;
    }
}

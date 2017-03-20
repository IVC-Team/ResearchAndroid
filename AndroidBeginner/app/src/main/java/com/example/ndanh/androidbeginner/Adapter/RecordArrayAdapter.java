package com.example.ndanh.androidbeginner.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ndanh.androidbeginner.Model.Record;
import com.example.ndanh.androidbeginner.R;

import java.util.List;

/**
 * Created by ndanh on 3/20/2017.
 */

public class RecordArrayAdapter extends ArrayAdapter<Record> {
    private TextView txtUserTime;
    private Context context = null;
    private List<Record> myArray = null;
    int layoutId;

    public RecordArrayAdapter(Context context, int resource, List<Record> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutId = resource;
        this.myArray = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layoutId, null);
try{
    TextView txtStartTime = (TextView) convertView.findViewById(R.id.txt_item_starttime);

    int starttime = Integer.parseInt(myArray.get(position).starttime);
    int hour = (int)(starttime / 100);
    int minute =  (int)(starttime % 100);
    String time = hour < 10 ? "0"+ String.valueOf(hour) : String.valueOf(hour) ;
    time += minute < 10 ? " : 0" + String.valueOf(minute) : " : " + String.valueOf(minute) ;
    txtStartTime.setText(time);

    TextView txtEndTime = (TextView) convertView.findViewById(R.id.txt_item_endtime);
    int endtime = Integer.parseInt(myArray.get(position).endtime);
    hour = (int)(endtime / 100);
    minute =  (int)(endtime % 100);
    time = hour < 10 ? "0"+ String.valueOf(hour) : String.valueOf(hour) ;
    time += minute < 10 ? " : 0" + String.valueOf(minute) : " : " + String.valueOf(minute) ;
    txtEndTime.setText(time);

    txtUserTime = (TextView) convertView.findViewById(R.id.txt_item_user);
    txtUserTime.setText(myArray.get(position).user);
}catch (Exception ex){
    ex.printStackTrace();
}

        return convertView;
    }
}

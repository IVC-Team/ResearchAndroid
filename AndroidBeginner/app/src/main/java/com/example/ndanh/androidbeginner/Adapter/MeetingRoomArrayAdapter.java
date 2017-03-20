package com.example.ndanh.androidbeginner.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ndanh.androidbeginner.Model.MeetingRoom;
import com.example.ndanh.androidbeginner.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ndanh on 3/17/2017.
 */

public class MeetingRoomArrayAdapter extends ArrayAdapter<MeetingRoom> {
    Context context=null;
    List<MeetingRoom> myArray=null;
    int layoutId;

    public MeetingRoomArrayAdapter(Context context, int layoutId, List<MeetingRoom> lst) {
        super(context, layoutId, lst);

        this.context = context;
        this.layoutId = layoutId;
        this.myArray = lst;
    }
    public View getView(int position, View convertView,
                        ViewGroup parent) {

        LayoutInflater inflater= ((Activity)context).getLayoutInflater();

        convertView=inflater.inflate(layoutId, null);

        if(myArray.size()>0 && position>=0)
        {

            final TextView txtAlias = (TextView)
                    convertView.findViewById(R.id.aliasname_room);
            final TextView txtDescribe =(TextView)
                    convertView.findViewById(R.id.describe_room);
            final MeetingRoom meetingRoom = myArray.get(position);

            txtAlias.setText(meetingRoom.aliasname.toString());
            txtDescribe.setText(meetingRoom.describe.toString());


        }

        return convertView;
    }
}

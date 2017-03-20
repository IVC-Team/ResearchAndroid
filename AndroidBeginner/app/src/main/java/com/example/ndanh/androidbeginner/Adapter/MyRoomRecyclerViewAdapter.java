package com.example.ndanh.androidbeginner.Adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ndanh.androidbeginner.Fragment.RoomFragment;
import com.example.ndanh.androidbeginner.Model.MeetingRoom;
import com.example.ndanh.androidbeginner.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MyRoomRecyclerViewAdapter extends RecyclerView.Adapter<MyRoomRecyclerViewAdapter.ViewHolder> {


    private List<MeetingRoom> mValues = new ArrayList<MeetingRoom>();
    private final RoomFragment.OnListFragmentInteractionListener mListener;

    public MyRoomRecyclerViewAdapter(List<MeetingRoom> values, RoomFragment.OnListFragmentInteractionListener listener) {
        this.mValues = values;
        mListener = listener;
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_room, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mDescribeView.setText(mValues.get(position).describe);
        holder.mAliasnameView.setText(mValues.get(position).aliasname);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mDescribeView;
        public final TextView mAliasnameView;
        public MeetingRoom mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mDescribeView = (TextView) view.findViewById(R.id.describe_room);
            mAliasnameView = (TextView) view.findViewById(R.id.aliasname_room);
        }

        @Override
        public String toString() {
            return super.toString() ;
        }
    }
}

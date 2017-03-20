package com.example.ndanh.androidbeginner.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ndanh.androidbeginner.Activity.MainActivity;
import com.example.ndanh.androidbeginner.Adapter.MyRoomRecyclerViewAdapter;
import com.example.ndanh.androidbeginner.Model.MeetingRoom;
import com.example.ndanh.androidbeginner.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RoomFragment extends Fragment {

    private List<MeetingRoom> meetingRooms = new ArrayList<MeetingRoom>();
    private OnListFragmentInteractionListener mListener;
    private MyRoomRecyclerViewAdapter adapter;

    public RoomFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            adapter = new MyRoomRecyclerViewAdapter( meetingRooms , mListener);
            recyclerView.setAdapter(adapter);
            loadData();
        }
        return view;
    }


    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);

        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(MeetingRoom item);
    }

    private void loadData(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (meetingRooms){
                    getMeetingRooms();
                }
            }
        });
        thread.start();
    }

    private void getMeetingRooms(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("meetingrooms");
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                meetingRooms.clear();
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    meetingRooms.add(child.getValue(MeetingRoom.class));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}

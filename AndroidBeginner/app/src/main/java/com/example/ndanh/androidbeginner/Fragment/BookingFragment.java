package com.example.ndanh.androidbeginner.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ndanh.androidbeginner.Adapter.RecordArrayAdapter;
import com.example.ndanh.androidbeginner.Model.MeetingRoom;
import com.example.ndanh.androidbeginner.Model.Record;
import com.example.ndanh.androidbeginner.R;
import com.example.ndanh.androidbeginner.Supporter.Common;
import com.example.ndanh.androidbeginner.Supporter.DatePicker;
import com.example.ndanh.androidbeginner.Supporter.EXTRAKEY;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookingFragment extends Fragment {
    private Button buttonRegister, buttonCancel;
    private TextView txtName,txtDateChosen,txtStartTime, txtEndTime;
    private ListView lstView;
    private RecordArrayAdapter adapter;
    private List<Record> lsRecordList = new ArrayList<Record>();
    private BookingFragment.OnListFragmentInteractionListener mListener;
    private String meetingRoomID, currentDate,currentUser ;
    private AtomicBoolean isSetStartTime = new AtomicBoolean(false),
            isSetEndTime= new AtomicBoolean(false),
            isSetDate= new AtomicBoolean(false);
    private Calendar cal = Calendar.getInstance();

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof BookingFragment.OnListFragmentInteractionListener) {
            mListener = (BookingFragment.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_booking, container, false);
        meetingRoomID =  getArguments().getString(EXTRAKEY.EXTRA_STRING_SELECTED_MEETINGROOM_ID);
        currentUser = getArguments().getString(EXTRAKEY.EXTRA_STRING_CURRENT_USER_ID);


        txtName = (TextView) view.findViewById(R.id.meeting_room_name);
        txtStartTime= (TextView) view.findViewById(R.id.txt_starttime);
        txtEndTime = (TextView) view.findViewById(R.id.txt_endtime);
        txtDateChosen = (TextView) view.findViewById(R.id.date_chosen);

        txtDateChosen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                lsRecordList.clear();
                txtStartTime.setText("");
                txtEndTime.setText("");
                adapter.notifyDataSetChanged();
                getRecords();
            }
        });
        txtDateChosen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        txtStartTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() != 0){
                    isSetStartTime.set(true);
                    notificateBtnRegister();
                }else {
                    isSetStartTime.set(false);
                    notificateBtnRegister();
                }
            }
        });
        txtStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialogStartTime();
            }
        });
        txtEndTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() != 0){
                    isSetEndTime.set(true);
                    notificateBtnRegister();
                }else {
                    isSetEndTime.set(false);
                    notificateBtnRegister();
                }
            }
        });
        txtEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialogEndTime();
            }
        });

        buttonRegister = (Button) view.findViewById(R.id.button_register);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
        buttonCancel = (Button) view.findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        txtDateChosen = (TextView) view.findViewById(R.id.date_chosen);
        txtDateChosen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        lstView = (ListView)view.findViewById(R.id.lst_registed);
        adapter = new RecordArrayAdapter((Context)this.mListener,R.layout.item_record,lsRecordList);
        lstView.setAdapter(adapter);
        setTxtDateChosen(cal);
        loadData();

        return view;
    }

    private void Register() {
        AtomicBoolean isValid = new AtomicBoolean(true);
        String strEndTime = Common.parseTimeToString(Common.parseStringToTime(txtEndTime.getText().toString()),"");
        String strStartTime = Common.parseTimeToString(Common.parseStringToTime(txtStartTime.getText().toString()),"");
        int endTime = Integer.parseInt(strEndTime);
        int startTime = Integer.parseInt(strStartTime);

        for(Record record : lsRecordList){
            int rendTime = Integer.parseInt(record.endtime);
            int rstartTime = Integer.parseInt(record.starttime);
            if( startTime >= rstartTime && startTime <= rendTime ){
                isValid.set(false);
            }
            if( endTime >= rstartTime && endTime <= rendTime ){
                isValid.set(false);
            }
        }
        if(isValid.get()){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("records");
            String key = myRef.push().getKey();
            myRef.child(key).setValue(new Record(currentDate, strStartTime, strEndTime, meetingRoomID,currentUser ));
            Toast.makeText((Context) mListener, "Success",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText((Context) mListener, "Failed",Toast.LENGTH_LONG).show();
        }

    }

    private void loadData(){
        getMeetingRoomInfo();
        getRecords();
    }

    private void getRecords() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("records");
        myRef.orderByChild("date").equalTo(currentDate).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lsRecordList.clear();
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    if (child.getValue(Record.class).meetingroom.equals(meetingRoomID)) {
                        lsRecordList.add(child.getValue(Record.class));
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getMeetingRoomInfo(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("meetingrooms");
        myRef.orderByChild("id").equalTo(meetingRoomID).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    txtName.setText(child.getValue(MeetingRoom.class).aliasname);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction();
    }

    public void showDatePickerDialog()
    {
        DatePickerDialog.OnDateSetListener callback=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                cal.set(year,month,dayOfMonth);
                setTxtDateChosen(cal);
            }

        };
        try{
            DatePickerDialog pic=new DatePickerDialog(
                    (Context)this.mListener,
                    callback, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
            pic.show();
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public void showTimePickerDialogStartTime()
    {
        final TimePickerDialog.OnTimeSetListener callback=new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view,
                                  int hourOfDay, int minute) {
                cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),hourOfDay, minute);
                setTxtTime(txtStartTime, cal);
            }
        };
        TimePickerDialog time=new TimePickerDialog(
                (Context)this.mListener,
                callback, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);
        time.show();
    }
    public void showTimePickerDialogEndTime()
    {
        TimePickerDialog.OnTimeSetListener callback=new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view,
                                  int hourOfDay, int minute) {
                cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),hourOfDay, minute);
                setTxtTime(txtEndTime, cal);
            }
        };
        TimePickerDialog time=new TimePickerDialog(
                (Context)this.mListener,
                callback, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);
        time.show();
    }

    private void setTxtDateChosen(Calendar date){
        currentDate = Common.parseDateToString(date,"", false);
        txtDateChosen.setText(Common.parseDateToString(date,"/", true));
    }

    private void setTxtTime(TextView view,Calendar date){
        view.setText(Common.parseTimeToString(date," : "));
    }

    private void notificateBtnRegister(){
        if(isSetStartTime.get() && isSetEndTime.get()){
            buttonRegister.setEnabled(true);
        }
        else{
            buttonRegister.setEnabled(false);
        }
    }
}

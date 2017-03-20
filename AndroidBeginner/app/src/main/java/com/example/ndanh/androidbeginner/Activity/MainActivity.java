package com.example.ndanh.androidbeginner.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import com.example.ndanh.androidbeginner.Base.NavigationActivity;
import com.example.ndanh.androidbeginner.Fragment.BookingFragment;
import com.example.ndanh.androidbeginner.Fragment.MyFragment;
import com.example.ndanh.androidbeginner.Fragment.RoomFragment;
import com.example.ndanh.androidbeginner.Model.MeetingRoom;
import com.example.ndanh.androidbeginner.R;
import com.example.ndanh.androidbeginner.Supporter.EXTRAKEY;


public class MainActivity extends NavigationActivity implements RoomFragment.OnListFragmentInteractionListener ,
        BookingFragment.OnListFragmentInteractionListener {
    private final static String TAG_MY_CLASS = "MainActivity";

    FragmentManager manager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ListUsers();
        //getSupportFragmentManager().beginTransaction().add(R.id.container, new MyFragment(), "booking").addToBackStack(null).commit();
    }

    @Override
    protected void Logout() {
        finish();
    }

    @Override
    protected void ListMeetingRooms() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new RoomFragment(), "booking").addToBackStack(null).commit();
    }

    @Override
    protected void ListUsers() {
        MyFragment fragment = new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAKEY.EXTRA_STRING_CURRENT_USER_ID,getIntent().getStringExtra(EXTRAKEY.EXTRA_STRING_CURRENT_USER_ID));
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, "booking").addToBackStack(null).commit();
    }

    @Override
    protected void Setting() {

    }

    @Override
    public void onListFragmentInteraction() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new BookingFragment(), "booking").addToBackStack(null).commit();
    }

    @Override
    public void onListFragmentInteraction(MeetingRoom meetingRoom) {

        BookingFragment fragment = new BookingFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAKEY.EXTRA_STRING_SELECTED_MEETINGROOM_ID, meetingRoom.id);
        bundle.putString(EXTRAKEY.EXTRA_STRING_CURRENT_USER_ID,getIntent().getStringExtra(EXTRAKEY.EXTRA_STRING_CURRENT_USER_ID));
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, "booking").addToBackStack(null).commit();
    }
}

package com.example.ndanh.androidbeginner.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by ndanh on 3/3/2017.
 */


/**
 * Created by ndanh on 3/2/2017.
 */

public class MeetingRoom  {


    public String id;
    public String describe;
    public String aliasname;
    public String name;


    public MeetingRoom( String name,String describe, String aliasname) {
        this.describe = describe;
        this.name = name;
        this.aliasname = aliasname;
    }

    public MeetingRoom() {
    }
}

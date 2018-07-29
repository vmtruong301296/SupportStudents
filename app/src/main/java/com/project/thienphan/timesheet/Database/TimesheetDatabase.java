package com.project.thienphan.timesheet.Database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TimesheetDatabase {

    public static DatabaseReference getTimesheetDatabase(){
        return FirebaseDatabase.getInstance().getReference();
    }
}

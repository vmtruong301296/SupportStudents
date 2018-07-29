package com.project.thienphan.timesheet.View;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Database.TimesheetDatabase;
import com.project.thienphan.timesheet.Model.Subject;
import com.project.thienphan.timesheet.Model.TimesheetItem;
import com.rilixtech.materialfancybutton.MaterialFancyButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateTimesheet extends AppCompatActivity {

    private DatabaseReference mydb;
    private ArrayList<String> subjectList;
    private Spinner mySpinner;
    private ArrayList<String> spinnerList;
    private ArrayAdapter<String> adapterSpinner;

    private MaterialFancyButton btnCreateTimesheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_timesheet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        this.mydb = TimesheetDatabase.getTimesheetDatabase();
        SetupView();
        SetupData();
        addEvents();
    }

    private void SetupData() {
        subjectList = new ArrayList<>();
        spinnerList = new ArrayList<>();
        spinnerList.add(this.getString(R.string.select_subject));

        adapterSpinner = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,spinnerList);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        mySpinner.setAdapter(adapterSpinner);
    }

    private void SetupView() {
        mySpinner = findViewById(R.id.spn_create_ts_subject);
        btnCreateTimesheet = findViewById(R.id.btn_create_ts);
    }

    private void addEvents() {
        this.getSubjects(this.getString(R.string.CHILD_SUBJECTS));
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.rgb(00, 00, 00));
                adapterSpinner.notifyDataSetChanged();
                if (subjectList.size() > 0 && i > 0){
                    Toast.makeText(getApplicationContext(),subjectList.get(i-1),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnCreateTimesheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handingCreateTimesheet();
            }
        });
    }

    private void handingCreateTimesheet() {

    }

    private void getSubjects(String key) {
        this.mydb.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    subjectList.add(snapshot.getKey().toString());
                    spinnerList.add(snapshot.getValue().toString());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

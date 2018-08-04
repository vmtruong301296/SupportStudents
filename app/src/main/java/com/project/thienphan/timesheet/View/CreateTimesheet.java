package com.project.thienphan.timesheet.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Adapter.AddTimesheetAdapter;
import com.project.thienphan.timesheet.Common.ServiceType;
import com.project.thienphan.timesheet.Database.TimesheetDatabase;
import com.project.thienphan.timesheet.Model.Subject;
import com.project.thienphan.timesheet.Model.TimesheetItem;
import com.project.thienphan.timesheet.Service.StudentSevice;
import com.project.thienphan.timesheet.Support.InfoDialog;
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

    private RecyclerView rcvCreateTimesheet;
    private AddTimesheetAdapter adapterRecyclerview;
    ArrayList<TimesheetItem> recyclerviewList;

    private int currentPosition = 0;

    private MaterialFancyButton btnCreateTimesheet;
    LinearLayout lnlAddtsProgress;

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

        adapterSpinner = new ArrayAdapter<String>(CreateTimesheet.this,android.R.layout.simple_list_item_1,spinnerList);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        mySpinner.setAdapter(adapterSpinner);

        recyclerviewList = new ArrayList<>();
        adapterRecyclerview = new AddTimesheetAdapter(recyclerviewList, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                btnCreateTimesheet.setEnabled(true);
                currentPosition = i;
                adapterRecyclerview.notifyDataSetChanged();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvCreateTimesheet.setLayoutManager(layoutManager);
        rcvCreateTimesheet.setAdapter(adapterRecyclerview);
    }

    private void SetupView() {
        mySpinner = findViewById(R.id.spn_create_ts_subject);
        btnCreateTimesheet = findViewById(R.id.btn_create_ts);
        btnCreateTimesheet.setEnabled(false);
        rcvCreateTimesheet = findViewById(R.id.rcv_create_timesheet);
        lnlAddtsProgress = findViewById(R.id.lnl_add_ts_progress);
    }

    private void addEvents() {
        this.getSubjects(this.getString(R.string.CHILD_SUBJECTS));
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.rgb(00, 00, 00));
                adapterSpinner.notifyDataSetChanged();
                if (subjectList.size() > 0 && i > 0){
                    RegisterSubjectSelected(subjectList.get(i-1));
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

    private void RegisterSubjectSelected(String s) {
        this.mydb.child(this.getString(R.string.CHILD_CLASS)).child(s).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recyclerviewList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()){
                    TimesheetItem timesheet = item.getValue(TimesheetItem.class);
                    recyclerviewList.add(timesheet);
                }
                adapterRecyclerview.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void handingCreateTimesheet() {
        lnlAddtsProgress.setVisibility(View.VISIBLE);
        this.mydb.child(getString(R.string.CHILD_TIMESHEET)).child("thienphan").push().setValue(recyclerviewList.get(currentPosition));
        lnlAddtsProgress.setVisibility(View.GONE);
        InfoDialog.ShowInfoDiaLog(CreateTimesheet.this,"Thông báo","Thêm thành công");
        btnCreateTimesheet.setEnabled(false);
    }

    private void getSubjects(String key) {
        this.mydb.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()){
                    spinnerList.add(item.getValue().toString());
                    subjectList.add(item.getKey().toString());
                }
                adapterSpinner.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                InfoDialog.ShowInfoDiaLog(CreateTimesheet.this,"Lỗi",databaseError.toString());
            }
        });
    }
}


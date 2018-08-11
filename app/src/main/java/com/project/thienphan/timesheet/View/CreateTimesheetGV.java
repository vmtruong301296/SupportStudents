package com.project.thienphan.timesheet.View;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Adapter.AddTimesheetAdapter;
import com.project.thienphan.timesheet.Database.TimesheetDatabase;
import com.project.thienphan.timesheet.Support.InfoDialog;
import com.rilixtech.materialfancybutton.MaterialFancyButton;

import java.util.ArrayList;

public class CreateTimesheetGV extends AppCompatActivity {

    LinearLayout lnlAddtsProgress;
    private DatabaseReference mydb;
    private ArrayList<String> subjectList;
    private ArrayList<String> daysList;
    private ArrayList<String> timeList;
    private ArrayList<String> locationList;
    private Spinner mySpinner;
    private Spinner myDays;
    private Spinner myTimes;
    private Spinner myLocation;
    private ArrayList<String> spinnerList;
    private ArrayList<String> spinnerListday;
    private ArrayList<String> spinnerListtime;
    private ArrayList<String> spinnerListlocation;
    private ArrayAdapter<String> adapterSpinner;
    private ArrayAdapter<String> adapterSpinnerday;
    private ArrayAdapter<String> adapterSpinnertime;
    private ArrayAdapter<String> adapterSpinnerlocation;
    // ArrayList<TimesheetItem> recyclerviewList;
    //private RecyclerView rcvCreateTimesheet;
    private AddTimesheetAdapter adapterRecyclerview;
    private int currentPosition = 0;
    private MaterialFancyButton btnCreateTimesheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_timesheet_gv);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.mydb = TimesheetDatabase.getTimesheetDatabase();


        SetupView();
        SetupData();
        addEvents();

    }

    private void SetupData() {
        subjectList = new ArrayList<>();
        daysList = new ArrayList<>();
        timeList = new ArrayList<>();
        locationList = new ArrayList<>();

        spinnerList = new ArrayList<>();
        spinnerListday = new ArrayList<>();
        spinnerListtime = new ArrayList<>();
        spinnerListlocation = new ArrayList<>();

//        spinnerList.add(this.getString(R.string.select_subject));
//        spinnerListday.add(this.getString(R.string.select_days));
//        spinnerListtime.add(this.getString(R.string.select_times));
//        spinnerListlocation.add(this.getString(R.string.select_location));

        adapterSpinner = new ArrayAdapter<String>(CreateTimesheetGV.this, android.R.layout.simple_list_item_1, spinnerList);
        adapterSpinnerday = new ArrayAdapter<String>(CreateTimesheetGV.this, android.R.layout.simple_list_item_1, spinnerListday);
        adapterSpinnertime = new ArrayAdapter<String>(CreateTimesheetGV.this, android.R.layout.simple_list_item_1, spinnerListtime);
        adapterSpinnerlocation = new ArrayAdapter<String>(CreateTimesheetGV.this, android.R.layout.simple_list_item_1, spinnerListlocation);

        adapterSpinner.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        adapterSpinnerday.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        adapterSpinnertime.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        adapterSpinnerlocation.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        mySpinner.setAdapter(adapterSpinner);
        myDays.setAdapter(adapterSpinnerday);
        myTimes.setAdapter(adapterSpinnertime);
        myLocation.setAdapter(adapterSpinnerlocation);


        //recyclerviewList = new ArrayList<>();
//        adapterRecyclerview = new AddTimesheetAdapter(recyclerviewList, new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                btnCreateTimesheet.setEnabled(true);
//                currentPosition = i;
//                adapterRecyclerview.notifyDataSetChanged();
//            }
//        });
        //LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        // layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //rcvCreateTimesheet.setLayoutManager(layoutManager);
        //rcvCreateTimesheet.setAdapter(adapterRecyclerview);
    }

    private void SetupView() {
        mySpinner = findViewById(R.id.spn_create_ts_subject);
        myDays = findViewById(R.id.spn_create_ts_dayofweek);
        myTimes = findViewById(R.id.spn_create_ts_times);
        myLocation = findViewById(R.id.spn_create_ts_location);

        btnCreateTimesheet = findViewById(R.id.btn_create_tsgv);
        // btnCreateTimesheet.setEnabled(false);
        //rcvCreateTimesheet = findViewById(R.id.rcv_create_timesheet);
        lnlAddtsProgress = findViewById(R.id.lnl_add_ts_progress);
    }

    private void addEvents() {
        this.getSubjects(this.getString(R.string.CHILD_SUBJECTS));

//        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.rgb(00, 00, 00));
//                adapterSpinner.notifyDataSetChanged();
//                if (subjectList.size() > 0 && i > 0) {
//                  //  RegisterSubjectSelected(subjectList.get(i - 1));
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        this.getSubjects1(this.getString(R.string.CHILD_DAYOFWEEK));
        myDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.rgb(00, 00, 00));
                adapterSpinnerday.notifyDataSetChanged();


                if (daysList.size() > 0 && i >= 0) {
                    // RegisterSubjectSelected(daysList.get(i));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        this.getSubjects2(this.getString(R.string.CHILD_TIMES));
//        myTimes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.rgb(00, 00, 00));
//                adapterSpinnertime.notifyDataSetChanged();
//                if (timeList.size() > 0 && i > 0) {
//                   // RegisterSubjectSelected(timeList.get(i - 1));
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        this.getSubjects3(this.getString(R.string.CHILD_LOCATION));
//        myLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.rgb(00, 00, 00));
//                adapterSpinnerlocation.notifyDataSetChanged();
//                if (locationList.size() > 0 && i > 0) {
//                    //RegisterSubjectSelected(locationList.get(i - 1));
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


        if (mySpinner != null && myDays != null && myTimes != null && myLocation != null) {
            btnCreateTimesheet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // handingCreateTimesheet();
                }
            });
        } else {
            InfoDialog.ShowInfoDiaLog(CreateTimesheetGV.this, "Thông báo", "Bạn chưa nhập đầy đủ thông tin");
        }
    }


//    private void RegisterSubjectSelected(String s) {
//        this.mydb.child(this.getString(R.string.CHILD_CLASS)).child(s).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                recyclerviewList.clear();
//                for (DataSnapshot item : dataSnapshot.getChildren()){
//                    TimesheetItem timesheet = item.getValue(TimesheetItem.class);
//                    recyclerviewList.add(timesheet);
//                }
//                adapterRecyclerview.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    private void handingCreateTimesheet() {
        lnlAddtsProgress.setVisibility(View.VISIBLE);
        //this.mydb.child(getString(R.string.CHILD_CLASS)).child("").push().setValue(mySpinner,myDays,myTimes,myLocation));
        //this.mydb.child(getString(R.string.CHILD_CLASS)).child("").push().setValue(recyclerviewList.get(currentPosition));
        lnlAddtsProgress.setVisibility(View.GONE);
        InfoDialog.ShowInfoDiaLog(CreateTimesheetGV.this, "Thông báo", "Thêm thành công");
        btnCreateTimesheet.setEnabled(false);
    }


    private void getSubjects(String key) {
        this.mydb.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    spinnerList.add(item.getValue().toString());
                    subjectList.add(item.getKey().toString());
                }
                adapterSpinner.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                InfoDialog.ShowInfoDiaLog(CreateTimesheetGV.this, "Lỗi", databaseError.toString());
            }
        });
    }

    private void getSubjects1(String key) {
        this.mydb.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    spinnerListday.add(item.getValue().toString());
                    daysList.add(item.getKey().toString());
                }
                adapterSpinnerday.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                InfoDialog.ShowInfoDiaLog(CreateTimesheetGV.this, "Lỗi", databaseError.toString());
            }
        });
    }

    private void getSubjects2(String key) {
        this.mydb.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    spinnerListtime.add(item.getValue().toString());
                    timeList.add(item.getKey().toString());
                }
                adapterSpinnertime.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                InfoDialog.ShowInfoDiaLog(CreateTimesheetGV.this, "Lỗi", databaseError.toString());
            }
        });
    }

    private void getSubjects3(String key) {
        this.mydb.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    spinnerListlocation.add(item.getValue().toString());
                    locationList.add(item.getKey().toString());
                }
                adapterSpinnerlocation.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                InfoDialog.ShowInfoDiaLog(CreateTimesheetGV.this, "Lỗi", databaseError.toString());
            }
        });
    }
}


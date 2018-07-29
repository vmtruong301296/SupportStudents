package com.project.thienphan.timesheet.View;

import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Adapter.TimesheetAdapter;
import com.project.thienphan.timesheet.Model.TimesheetItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rcvTimesheet;
    ArrayList<TimesheetItem> timesheetList;
    TimesheetAdapter timesheetAdapter;

    Button btnMonday,btnTuesday,btnWednesday,btnThurday,btnFriday,btnSaturday;
    Button btnActive;
    Button btnCreate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addControls() {
        rcvTimesheet = findViewById(R.id.rcv_timesheet);
        SetupButton();
        timesheetList = new ArrayList<>();
        timesheetAdapter = new TimesheetAdapter(timesheetList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvTimesheet.setLayoutManager(layoutManager);
        rcvTimesheet.setAdapter(timesheetAdapter);
        GetData();
    }

    private void SetupButton() {
        btnMonday = findViewById(R.id.btn_ts_monday);
        btnMonday.setEnabled(false);
        btnActive = btnMonday;
        btnTuesday = findViewById(R.id.btn_ts_tuesday);
        btnWednesday = findViewById(R.id.btn_ts_wednesday);
        btnThurday = findViewById(R.id.btn_ts_thurday);
        btnFriday = findViewById(R.id.btn_ts_friday);
        btnSaturday = findViewById(R.id.btn_ts_saturday);
        btnCreate = findViewById(R.id.btn_ts_create);
    }

    private void GetData() {
        timesheetList.add(new TimesheetItem(
                "CT250",
                "Lập trình căn bản",
                5,
                "Nguyễn Thanh Phương",
                "123",
                "105/C1"));
        timesheetList.add(new TimesheetItem(
                "CT123",
                "Lập trình hướng đối tượng",
                2,
                "Trần Văn Khoa",
                "678",
                "210/B1"));
        timesheetList.add(new TimesheetItem(
                "CT101",
                "Giao diện người máy",
                4,
                "Nguyễn Thị Thu An",
                "345",
                "103/C1"));
        timesheetList.add(new TimesheetItem(
                "CT172",
                "Cấu trúc dữ liệu",
                3,
                "Nguyễn Văn Tiến",
                "123",
                "302/B1"));
        timesheetAdapter.notifyDataSetChanged();
    }

    private void addEvents() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == btnMonday){
                    setActionButton(btnMonday);
                }
                else if (view == btnTuesday){
                    setActionButton(btnTuesday);
                }
                else if (view == btnWednesday){
                    setActionButton(btnWednesday);
                }
                else if (view == btnThurday){
                    setActionButton(btnThurday);
                }
                else if (view == btnFriday){
                    setActionButton(btnFriday);
                }
                else if (view == btnSaturday){
                    setActionButton(btnSaturday);
                }
            }
        };
        btnMonday.setOnClickListener(onClickListener);
        btnTuesday.setOnClickListener(onClickListener);
        btnWednesday.setOnClickListener(onClickListener);
        btnThurday.setOnClickListener(onClickListener);
        btnFriday.setOnClickListener(onClickListener);
        btnSaturday.setOnClickListener(onClickListener);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CreateTimesheet.class);
                startActivity(intent);
            }
        });
    }

    private void setActionButton(Button button) {
        button.setEnabled(false);
        button.setTextColor(getResources().getColor(R.color.dayofweek));
        btnActive.setTextColor(getResources().getColor(android.R.color.white));
        btnActive.setEnabled(true);
        btnActive = button;
    }
}

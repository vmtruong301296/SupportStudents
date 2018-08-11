package com.project.thienphan.timesheet.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Adapter.TimesheetAdapter;
import com.project.thienphan.timesheet.Database.TimesheetDatabase;
import com.project.thienphan.timesheet.Model.TimesheetItem;
import com.project.thienphan.timesheet.Support.InfoDialog;

import java.util.ArrayList;

public class MainActivityGV extends AppCompatActivity {

    //lay database in firebase
    DatabaseReference mydb = TimesheetDatabase.getTimesheetDatabase();

    // tao bien rcvThimesheet
    RecyclerView rcvTimesheet;

    // tao 1 mang timesheetList
    ArrayList<TimesheetItem> timesheetList;


    //tao bien timesheetAdapter
    TimesheetAdapter timesheetAdapter;


    // tao button ngay
    Button btnMonday, btnTuesday, btnWednesday, btnThurday, btnFriday, btnSaturday;

    // tao nut btnActive mac dinh
    Button btnActive;

    // tao nut button them
    Button btnCreate;

    Button btnSinhvien;
    // tao textView trống
    TextView txtListEmpty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gv);

        addControls();
        addEvents();
    }


    private void addControls() {

        // đặt nút khởi tạo = nút trong activity_main
        // Nút trống
        txtListEmpty = findViewById(R.id.tv_ts_empty_list);
        // khung chứa lịch học
        rcvTimesheet = findViewById(R.id.rcv_timesheet);

        SetupButton();

        // tạo 1 mảng động để lưu trữ các phần tử
        timesheetList = new ArrayList<>();


        timesheetAdapter = new TimesheetAdapter(timesheetList);

        // Sử dụng RecyclerView trong android
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvTimesheet.setLayoutManager(layoutManager);
        rcvTimesheet.setAdapter(timesheetAdapter);


        GetData(2);
    }


    //Đặt nút khởi tạo = những nút trong activity_main ( giao diện )
    private void SetupButton() {
        btnMonday = findViewById(R.id.btn_ts_monday);

        // vo hieu qua nut btnModay
        btnMonday.setEnabled(false);

        btnActive = btnMonday;

        btnTuesday = findViewById(R.id.btn_ts_tuesday);
        btnWednesday = findViewById(R.id.btn_ts_wednesday);
        btnThurday = findViewById(R.id.btn_ts_thurday);
        btnFriday = findViewById(R.id.btn_ts_friday);
        btnSaturday = findViewById(R.id.btn_ts_saturday);
        btnCreate = findViewById(R.id.btn_ts_create);
        btnSinhvien = findViewById(R.id.btn_ts_sinhvien);
    }

    // Chưa hiểu lắm trong hàm này
    private void GetData(final int dayofweek) {
        txtListEmpty.setVisibility(View.GONE);
        this.mydb.child(getString(R.string.CHILD_TIMESHEET)).child("votruong").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                timesheetList.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    TimesheetItem item = child.getValue(TimesheetItem.class);
                    timesheetList.add(item);
                }
                ArrayList<TimesheetItem> temp = TimesheetItem.getTimesheetByDayofWeek(timesheetList, dayofweek);
                timesheetList.clear();
                timesheetList.addAll(temp);
                timesheetAdapter.notifyDataSetChanged();
                if (timesheetList.size() == 0) {
                    txtListEmpty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                InfoDialog.ShowInfoDiaLog(MainActivityGV.this, "Lỗi", databaseError.toString());
            }
        });
    }

    //Tạo Events khi nhấn vào các ngày trong tuần
    private void addEvents() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            //Tạo event tương ứng với từng thứ trong tuần với biến GetData
            public void onClick(View view) {
                if (view == btnMonday) {
                    setActionButton(btnMonday);
                    GetData(2);
                } else if (view == btnTuesday) {
                    setActionButton(btnTuesday);
                    GetData(3);
                } else if (view == btnWednesday) {
                    setActionButton(btnWednesday);
                    GetData(4);
                } else if (view == btnThurday) {
                    setActionButton(btnThurday);
                    GetData(5);
                } else if (view == btnFriday) {
                    setActionButton(btnFriday);
                    GetData(6);
                } else if (view == btnSaturday) {
                    setActionButton(btnSaturday);
                    GetData(7);
                }
            }
        };

        // Setting sự kiện cho từng nút btn
        btnMonday.setOnClickListener(onClickListener);
        btnTuesday.setOnClickListener(onClickListener);
        btnWednesday.setOnClickListener(onClickListener);
        btnThurday.setOnClickListener(onClickListener);
        btnFriday.setOnClickListener(onClickListener);
        btnSaturday.setOnClickListener(onClickListener);


        // Setting sư kiện cho nút Thêm thời khóa biểu
        // Chuyển sang trang CreateTimesheet
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityGV.this, CreateTimesheetGV.class);
                startActivity(intent);
            }
        });

        btnSinhvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityGV.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //Setting hành động của Button
    private void setActionButton(Button button) {
        //Vô hiệu hóa nút đó
        button.setEnabled(false);

        //setting màu cho nút
        button.setTextColor(getResources().getColor(R.color.dayofweek));
        btnActive.setTextColor(getResources().getColor(android.R.color.white));


        // Sau khi setting màu thì chuyển trạng thái nút sáng True : Hoạt động
        btnActive.setEnabled(true);
        btnActive = button;
    }
}

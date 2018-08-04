package com.project.thienphan.timesheet.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Model.TimesheetItem;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AddTimesheetAdapter extends RecyclerView.Adapter<AddTimesheetAdapter.AddTimesheetHolder> {
    ArrayList<TimesheetItem> Data;
    Boolean[] isChecked;
    AdapterView.OnItemClickListener ItemChecked;

    public AddTimesheetAdapter(ArrayList<TimesheetItem> data, AdapterView.OnItemClickListener listener) {
        this.Data = data;
        ItemChecked = listener;
        isChecked = new Boolean[50];
        initChecked();
    }

    private void initChecked() {
        for (int i = 0; i < 50; i++){
            this.isChecked[i] = false;
        }
    }

    @NonNull
    @Override
    public AddTimesheetHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_create_timesheet,viewGroup,false);
        return new AddTimesheetHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AddTimesheetHolder addTimesheetHolder, int i) {
        addTimesheetHolder.txtDayofWeek.setText("Thứ " + Data.get(i).getDayofWeek());
        addTimesheetHolder.txtTeacherName.setText(Data.get(i).getSubjectTeacher());
        addTimesheetHolder.txtTime.setText(Data.get(i).getSubjectTime());
        addTimesheetHolder.txtLocation.setText(Data.get(i).getSubjectLocation());
        addTimesheetHolder.imgChecked.setVisibility(this.isChecked[i] ? View.VISIBLE : View.GONE);
        addTimesheetHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initChecked();
                isChecked[addTimesheetHolder.getAdapterPosition()] = true;
                ItemChecked.onItemClick(null,view,addTimesheetHolder.getAdapterPosition(),addTimesheetHolder.getItemId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    public class AddTimesheetHolder extends RecyclerView.ViewHolder{
        ImageView imgChecked;
        TextView txtDayofWeek,txtTeacherName,txtTime,txtLocation;
        View container;
        public AddTimesheetHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView;
            imgChecked = itemView.findViewById(R.id.img_add_ts_item_checked);
            imgChecked.setVisibility(View.GONE);
            txtDayofWeek = itemView.findViewById(R.id.tv_add_ts_item_dayofweek);
            txtTeacherName = itemView.findViewById(R.id.tv_add_ts_item_teacher_name);
            txtTime = itemView.findViewById(R.id.tv_add_ts_item_time);
            txtLocation = itemView.findViewById(R.id.tv_add_ts_item_location);
        }

    }
}

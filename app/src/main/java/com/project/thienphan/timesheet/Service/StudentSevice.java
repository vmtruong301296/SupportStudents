package com.project.thienphan.timesheet.Service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.project.thienphan.supportstudent.R;
import com.project.thienphan.timesheet.Common.ServiceType;
import com.project.thienphan.timesheet.Database.TimesheetDatabase;
import com.project.thienphan.timesheet.Interface.ITimesheet;

public class StudentSevice extends AsyncTask<String, Void, String>{

    private DatabaseReference mydb;
    private Activity context;
    private int serviceType;
    private ProgressDialog dialog;
    private String result;
    public ITimesheet iTimesheet = null;

    public StudentSevice(Activity context, int serviceType, ITimesheet iTimesheet) {
        this.context = context;
        this.serviceType = serviceType;
        this.iTimesheet = iTimesheet;
        this.dialog = new ProgressDialog(context);
        this.mydb = TimesheetDatabase.getTimesheetDatabase();
        this.result = "";
    }

    public StudentSevice() {
        super();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.show();
    }

    @Override
    protected String doInBackground(String... key) {
        CheckServiceType(key);
        if (result != null){
            return result;
        }
        return "";
    }

    @Override
    protected void onPostExecute(String json) {
        super.onPostExecute(json);
        if (dialog!=null && dialog.isShowing() && !json.isEmpty()){
            dialog.dismiss();
        }
        else {
            dialog.dismiss();
            Toast.makeText(this.context, this.context.getString(R.string.request_failed),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(String json) {
        super.onCancelled(json);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    private void CheckServiceType(String... key) {
        switch (this.serviceType){
            case ServiceType.GET_SUBJECTS:
                getSubjects(key[0]);
                break;
            default:
                break;
        }
    }

    private void getSubjects(String key){
        this.mydb.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Gson gson = new Gson();
                result = gson.toJson(dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

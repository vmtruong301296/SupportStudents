package com.project.thienphan.timesheet.Service;

import android.content.Context;
import android.os.AsyncTask;

public class StudentSevice extends AsyncTask<Void, Integer, Void>{

    private Context context;
    private int serviceType;

    public StudentSevice(Context context, int serviceType) {
        this.context = context;
        this.serviceType = serviceType;
    }

    public StudentSevice() {
        super();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Void aVoid) {
        super.onCancelled(aVoid);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }
}

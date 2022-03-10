package com.example.equipmentinspection.database.async;

import android.content.Context;
import android.os.AsyncTask;

import com.example.equipmentinspection.database.AppDatabase;
import com.example.equipmentinspection.database.entity.InspectionEntity;
import com.example.equipmentinspection.database.entity.InspectorEntity;
import com.example.equipmentinspection.util.OnAsyncEventListener;

public class InspectorUpdate extends AsyncTask<InspectorEntity, Void, Void> {

        private AppDatabase database;
    private OnAsyncEventListener callback;
    private Exception exception;

    public InspectorUpdate(Context context, OnAsyncEventListener callback) {
        database = AppDatabase.getInstance(context);
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(InspectorEntity... params) {
        try {
            for (InspectorEntity inspector : params)
                database.inspectorDao().update(inspector);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback != null) {
            if (exception == null) {
                callback.onSuccess();
            } else {
                callback.onFailure(exception);
            }
        }
    }
}
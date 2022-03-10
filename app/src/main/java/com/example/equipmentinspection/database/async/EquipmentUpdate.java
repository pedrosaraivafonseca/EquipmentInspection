package com.example.equipmentinspection.database.async;

import android.content.Context;
import android.os.AsyncTask;

import com.example.equipmentinspection.database.AppDatabase;
import com.example.equipmentinspection.database.entity.EquipmentEntity;
import com.example.equipmentinspection.util.OnAsyncEventListener;

public class EquipmentUpdate extends AsyncTask<EquipmentEntity, Void, Void> {

    private AppDatabase database;
    private OnAsyncEventListener callback;
    private Exception exception;

    public EquipmentUpdate(Context context, OnAsyncEventListener callback) {
        database = AppDatabase.getInstance(context);
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(EquipmentEntity... params) {
        try {
            for (EquipmentEntity equipment : params)
                database.equipmentDao().update(equipment);
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
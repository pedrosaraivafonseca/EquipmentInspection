package com.example.equipmentinspection.database.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.equipmentinspection.BaseApp;
import com.example.equipmentinspection.database.AppDatabase;
import com.example.equipmentinspection.database.async.InspectorCreate;
import com.example.equipmentinspection.database.async.InspectorDelete;
import com.example.equipmentinspection.database.async.InspectorUpdate;
import com.example.equipmentinspection.database.entity.InspectorEntity;
import com.example.equipmentinspection.util.OnAsyncEventListener;

import java.util.List;

public class InspectorRepository {

    private static InspectorRepository instance;

    private InspectorRepository() {}

    public static InspectorRepository getInstance() {
        if (instance == null) {
            synchronized (InspectorRepository.class) {
                if (instance == null) {
                    instance = new InspectorRepository();
                }
            }
        }
        return instance;
    }


    public LiveData<InspectorEntity> getInspector(final Long inspectorId, Application application) {
        return ((BaseApp) application).getDatabase().inspectorDao().getById(inspectorId);
    }

    public LiveData<InspectorEntity> getInspectorByLogin(final String mail, String password, Application application) {
        return ((BaseApp) application).getDatabase().inspectorDao().getByLogin(mail, password);
    }

    public LiveData<List<InspectorEntity>> getAllInspector(Context context) {
        return AppDatabase.getInstance(context).inspectorDao().getAll();
    }

    public void insert(final InspectorEntity inspector, OnAsyncEventListener callback, Context context) {
        new InspectorCreate(context, callback).execute(inspector);
    }

    public void update(final InspectorEntity inspector, OnAsyncEventListener callback, Context context) {
        new InspectorUpdate(context, callback).execute(inspector);
    }

    public void delete(final InspectorEntity inspector, OnAsyncEventListener callback, Context context) {
        new InspectorDelete(context, callback).execute(inspector);
    }
}

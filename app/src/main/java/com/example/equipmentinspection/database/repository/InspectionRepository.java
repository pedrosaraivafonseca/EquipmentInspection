package com.example.equipmentinspection.database.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.equipmentinspection.database.AppDatabase;
import com.example.equipmentinspection.database.async.InspectionCreate;
import com.example.equipmentinspection.database.async.InspectionDelete;
import com.example.equipmentinspection.database.async.InspectionUpdate;
import com.example.equipmentinspection.database.entity.InspectionEntity;
import com.example.equipmentinspection.util.OnAsyncEventListener;

import java.util.List;

public class InspectionRepository {

    private static InspectionRepository instance;

    private InspectionRepository() {}

    public static InspectionRepository getInstance() {
        if (instance == null) {
            synchronized (InspectionRepository.class) {
                if (instance == null) {
                    instance = new InspectionRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<InspectionEntity> getInspection(final int id, Context context) {
        return AppDatabase.getInstance(context).inspectionDao().getById(id);
    }

    public LiveData<InspectionEntity> getInspection(final String status, Context context) {
        return AppDatabase.getInstance(context).inspectionDao().getByStatus(status);
    }

    public LiveData<List<InspectionEntity>> getAllInspection(Context context) {
        return AppDatabase.getInstance(context).inspectionDao().getAll();
    }

    public void insert(final InspectionEntity inspection, OnAsyncEventListener callback, Context context) {
        new InspectionCreate(context, callback).execute(inspection);
    }

    public void update(final InspectionEntity inspection, OnAsyncEventListener callback, Context context) {
        new InspectionUpdate(context, callback).execute(inspection);
    }

    public void delete(final InspectionEntity inspection, OnAsyncEventListener callback, Context context) {
        new InspectionDelete(context, callback).execute(inspection);
    }
}

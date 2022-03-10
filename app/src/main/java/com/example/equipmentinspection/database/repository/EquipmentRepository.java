package com.example.equipmentinspection.database.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.equipmentinspection.database.AppDatabase;
import com.example.equipmentinspection.database.async.EquipmentDelete;
import com.example.equipmentinspection.database.async.EquipmentUpdate;
import com.example.equipmentinspection.database.entity.EquipmentEntity;
import com.example.equipmentinspection.util.OnAsyncEventListener;
import com.example.equipmentinspection.database.async.EquipmentCreate;

import java.util.List;

public class EquipmentRepository {

    private static EquipmentRepository instance;

    private EquipmentRepository() {}

    public static EquipmentRepository getInstance() {
        if (instance == null) {
            synchronized (EquipmentRepository.class) {
                if (instance == null) {
                    instance = new EquipmentRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<EquipmentEntity> getEquipment(final int id, Context context) {
        return AppDatabase.getInstance(context).equipmentDao().getById(id);
    }

    public LiveData<List<EquipmentEntity>> getAllEquipment(Context context) {
        return AppDatabase.getInstance(context).equipmentDao().getAll();
    }

    public void insert(final EquipmentEntity equipment, OnAsyncEventListener callback, Context context) {
        new EquipmentCreate(context, callback).execute(equipment);
    }

    public void update(final EquipmentEntity equipment, OnAsyncEventListener callback, Context context) {
        new EquipmentUpdate(context, callback).execute(equipment);
    }

    public void delete(final EquipmentEntity equipment, OnAsyncEventListener callback, Context context) {
        new EquipmentDelete(context, callback).execute(equipment);
    }
}

package com.example.equipmentinspection;

import android.app.Application;

import com.example.equipmentinspection.database.AppDatabase;
import com.example.equipmentinspection.database.repository.EquipmentRepository;
import com.example.equipmentinspection.database.repository.InspectionRepository;
import com.example.equipmentinspection.database.repository.InspectorRepository;

/**
 * Android Application class. Used for accessing singletons.
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }


    public InspectorRepository getInspectorRepository() {
        return InspectorRepository.getInstance();
    }

    public EquipmentRepository getEquipmentRepository() {
        return EquipmentRepository.getInstance();
    }


    public InspectionRepository getInspectionRepository() {
        return InspectionRepository.getInstance();
    }
}
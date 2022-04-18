package com.example.equipmentinspection.database.repository;

import androidx.lifecycle.LiveData;

import com.example.equipmentinspection.database.entity.InspectionEntity;
import com.example.equipmentinspection.database.firebase.InspectionListLiveData;
import com.example.equipmentinspection.database.firebase.InspectionLiveData;
import com.example.equipmentinspection.util.OnAsyncEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    public LiveData<InspectionEntity> getInspection(final String id) {
        DatabaseReference reference = FirebaseDatabase.getInstance("https://equipment-inspection-604ff-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("inspection")
                .child(id);
        return new InspectionLiveData(reference);
    }

    public LiveData<InspectionEntity> getInspectionByStatus(final String status) {
        DatabaseReference reference = FirebaseDatabase.getInstance("https://equipment-inspection-604ff-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("inspection")
                .child(status);
        return new InspectionLiveData(reference);
    }

    public LiveData<List<InspectionEntity>> getAllInspection() {
        DatabaseReference reference = FirebaseDatabase.getInstance("https://equipment-inspection-604ff-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("inspection");
        return new InspectionListLiveData(reference);

    }

    public void insert(final InspectionEntity inspection, OnAsyncEventListener callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance("https://equipment-inspection-604ff-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("inspection");
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance("https://equipment-inspection-604ff-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("inspection")
                .child(key)
                .setValue(inspection, (databaseError, databaseReference) -> {
                    if (databaseError != null){
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final InspectionEntity inspection, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance("https://equipment-inspection-604ff-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("inspection")
                .child(inspection.getIdInspection())
                .updateChildren(inspection.map(), (databaseError, databaseReference) -> {
                    if (databaseError != null){
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final InspectionEntity inspection, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance("https://equipment-inspection-604ff-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("inspection")
                .child(inspection.getIdInspection())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError!= null){
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}

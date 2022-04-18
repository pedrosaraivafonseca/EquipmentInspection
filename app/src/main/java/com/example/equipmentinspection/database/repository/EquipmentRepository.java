package com.example.equipmentinspection.database.repository;

import androidx.lifecycle.LiveData;

import com.example.equipmentinspection.database.entity.EquipmentEntity;
import com.example.equipmentinspection.database.firebase.EquipmentListLiveData;
import com.example.equipmentinspection.database.firebase.EquipmentLiveData;
import com.example.equipmentinspection.util.OnAsyncEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    public LiveData<EquipmentEntity> getEquipment(final String id) {
        DatabaseReference reference = FirebaseDatabase.getInstance("https://equipment-inspection-604ff-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("equipments")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(id);
        return new EquipmentLiveData(reference);
    }

    public LiveData<List<EquipmentEntity>> getAllEquipment() {
        DatabaseReference reference = FirebaseDatabase.getInstance("https://equipment-inspection-604ff-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("equipments");
        return new EquipmentListLiveData(reference);
    }

    public void insert(final EquipmentEntity equipment, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance("https://equipment-inspection-604ff-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("equipment")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(equipment, (databaseError, databaseReference) -> {
                    if (databaseError != null){
                        callback.onFailure(databaseError.toException());
                        FirebaseAuth.getInstance().getCurrentUser().delete()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()){
                                    callback.onFailure(null);
                                } else {
                                        callback.onFailure(task.getException());
                                    }
                        });
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final EquipmentEntity equipment, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance("https://equipment-inspection-604ff-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("equipment")
                .child(equipment.getIdEquipment())
                .updateChildren(equipment.map(), (databaseError, databaseReference) -> {
                    if (databaseError != null){
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final EquipmentEntity equipment, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance("https://equipment-inspection-604ff-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("equipment")
                .child(equipment.getIdEquipment())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError!=null){
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}

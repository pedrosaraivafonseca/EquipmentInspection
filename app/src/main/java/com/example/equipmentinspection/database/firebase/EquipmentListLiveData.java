package com.example.equipmentinspection.database.firebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.equipmentinspection.database.entity.EquipmentEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EquipmentListLiveData extends LiveData<List<EquipmentEntity>> {

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public EquipmentListLiveData(DatabaseReference reference) {
        this.reference = reference;
    }

    private class MyValueEventListener implements ValueEventListener{

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            setValue(toEquipment(snapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    }

    private List<EquipmentEntity> toEquipment(DataSnapshot snapshot) {
        List<EquipmentEntity> equipments = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()){
            EquipmentEntity equipmentEntity = childSnapshot.getValue(EquipmentEntity.class);
            equipmentEntity.setIdEquipmentString(childSnapshot.getKey());
            equipments.add(equipmentEntity);
        }
        return equipments;
    }






}

package com.example.equipmentinspection.database.firebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.equipmentinspection.database.entity.EquipmentEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class EquipmentLiveData extends LiveData<EquipmentEntity> {

    private final DatabaseReference reference;
    private final EquipmentLiveData.MyValueEventListener listener = new EquipmentLiveData.MyValueEventListener();


    public EquipmentLiveData(DatabaseReference reference) {
        this.reference = reference;
    }

    private class MyValueEventListener implements ValueEventListener{

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            EquipmentEntity entity = snapshot.getValue(EquipmentEntity.class);
            entity.setIdEquipmentString(snapshot.getKey());
            setValue(entity);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    }
}

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
    private final String equipment;
    private final ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            setValue(toEquipment(snapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    private List<EquipmentEntity> toEquipment(DataSnapshot snapshot) {
        List<EquipmentEntity> equipments = new ArrayList<>();
        for (DataSnapshot childSnapchot : snapshot.getChildren()){
            EquipmentEntity equipmentEntity = childSnapchot.getValue(EquipmentEntity.class);
            equipmentEntity.setIdEquipmentString(childSnapchot.getKey());
            equipments.add(equipmentEntity);
        }
        return equipments;
    }


    public EquipmentListLiveData(DatabaseReference reference, String equipment) {
        this.reference = reference;
        this.equipment = equipment;
    }


}

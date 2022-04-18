package com.example.equipmentinspection.database.firebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.equipmentinspection.database.entity.InspectionEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class InspectionLiveData extends LiveData<InspectionEntity> {
    private final DatabaseReference reference;
    private final InspectionLiveData.MyValueEventListener listener = new InspectionLiveData.MyValueEventListener();


    public InspectionLiveData(DatabaseReference reference) {
        this.reference = reference;
    }

    private class MyValueEventListener implements ValueEventListener{

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            InspectionEntity entity = snapshot.getValue(InspectionEntity.class);
            entity.setIdInspection(snapshot.getKey());
            setValue(entity);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    }
}

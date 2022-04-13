package com.example.equipmentinspection.database.firebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.equipmentinspection.database.entity.InspectionEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class InspectionListLiveData extends LiveData<List<InspectionEntity>> {

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public InspectionListLiveData(DatabaseReference reference) {
        this.reference = reference;
    }

    private class MyValueEventListener implements ValueEventListener{

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            setValue(toInspection(snapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    }

    private List<InspectionEntity> toInspection(DataSnapshot snapshot) {
        List<InspectionEntity> inspections = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            InspectionEntity inspectionEntity = childSnapshot.getValue(InspectionEntity.class);
            inspectionEntity.setIdInspectionString(childSnapshot.getKey());
            inspections.add(inspectionEntity);
        }
        return inspections;
    }


}

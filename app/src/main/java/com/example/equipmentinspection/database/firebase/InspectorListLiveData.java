package com.example.equipmentinspection.database.firebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.equipmentinspection.database.entity.InspectorEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class InspectorListLiveData extends LiveData<List<InspectorEntity>> {

    private final DatabaseReference reference;

    private final MyValueEventListener listener = new MyValueEventListener();

    public InspectorListLiveData(DatabaseReference reference) {
        this.reference = reference;
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            setValue(toInspector(snapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    }

    private List<InspectorEntity> toInspector(DataSnapshot snapshot){
        List<InspectorEntity> inspectors = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()){
            InspectorEntity entity = childSnapshot.getValue(InspectorEntity.class);
            entity.setIdInspector(childSnapshot.getKey());
            inspectors.add(entity);
        }
        return inspectors;
    }


}

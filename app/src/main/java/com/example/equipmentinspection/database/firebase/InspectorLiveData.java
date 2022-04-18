package com.example.equipmentinspection.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.equipmentinspection.database.entity.InspectorEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class InspectorLiveData extends LiveData<InspectorEntity> {

    private final DatabaseReference reference;
    private final InspectorLiveData.MyValueEventListener listener = new InspectorLiveData.MyValueEventListener();


    public InspectorLiveData(DatabaseReference reference) {
        this.reference = reference;
    }

    @Override
    protected void onActive() {
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {

    }

    private class MyValueEventListener implements ValueEventListener{

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            InspectorEntity entity = snapshot.getValue(InspectorEntity.class);
            entity.setIdInspector(snapshot.getKey());
            setValue(entity);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    }
}

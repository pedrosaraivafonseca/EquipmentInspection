package com.example.equipmentinspection.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.equipmentinspection.database.entity.EquipmentEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class EquipmentLiveData extends LiveData<EquipmentEntity> {

    private final DatabaseReference reference;
    private final String owner;
    private final EquipmentLiveData.MyValueEventListener listener = new EquipmentLiveData.MyValueEventListener();


    public EquipmentLiveData(DatabaseReference reference) {
        this.owner = reference.getParent().getParent().getKey();
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

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    }
}

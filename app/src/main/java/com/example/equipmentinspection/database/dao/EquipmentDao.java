package com.example.equipmentinspection.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import com.example.equipmentinspection.database.entity.EquipmentEntity;
import com.example.equipmentinspection.database.entity.InspectionEntity;

@Dao
public interface EquipmentDao {
    @Query("SELECT * FROM equipments WHERE idEquipment = :id")
    LiveData<EquipmentEntity> getById(int id);

    @Query("SELECT * FROM equipments WHERE statusEquipment = :status")
    LiveData<EquipmentEntity> getByStatus(String status);

//    @Query("SELECT * FROM equipments WHERE nextInspectionDateEquipment = :date")
//    LiveData<List<InspectionEntity>> getByNextInspectionDate(String date);
//
//    @Query("SELECT * FROM equipments WHERE lastInspectionDateEquipment = :date")
//    LiveData<List<InspectionEntity>> getByLastInspectionDate(String date);

    @Query("SELECT * FROM equipments")
    LiveData<List<EquipmentEntity>> getAll();

    @Insert
    void insert(EquipmentEntity equipment);

    @Update
    void update(EquipmentEntity equipment);

    @Delete
    void delete(EquipmentEntity equipment);

    @Query("DELETE FROM equipments")
    void deleteAll();
}

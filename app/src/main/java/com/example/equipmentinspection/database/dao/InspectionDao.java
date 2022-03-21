package com.example.equipmentinspection.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import com.example.equipmentinspection.database.entity.InspectionEntity;

@Dao
public interface InspectionDao {
    @Query("SELECT * FROM inspections WHERE idInspection = :id")
    LiveData<InspectionEntity> getById(Long id);

    @Query("SELECT * FROM inspections WHERE idInspectorInspection = :idInspector")
    LiveData<List<InspectionEntity>> getByInspector(Long idInspector);

    @Query("SELECT * FROM inspections WHERE idEquipmentInspection = :idEquipment")
    LiveData<List<InspectionEntity>> getByEquipment(Long idEquipment);

    @Query("SELECT * FROM inspections WHERE statusInspection = :status")
    LiveData<InspectionEntity> getByStatus(String status);

    @Query("SELECT * FROM inspections")
    LiveData<List<InspectionEntity>> getAll();

    @Insert
    void insert(InspectionEntity inspection);

    @Update
    void update(InspectionEntity inspection);

    @Delete
    void delete(InspectionEntity inspection);

    @Query("DELETE FROM inspections")
    void deleteAll();
}

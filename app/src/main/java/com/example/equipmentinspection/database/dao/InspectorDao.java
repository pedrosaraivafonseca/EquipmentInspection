package com.example.equipmentinspection.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import com.example.equipmentinspection.database.entity.InspectorEntity;

@Dao
public interface InspectorDao {

    @Query("SELECT * FROM inspectors WHERE idInspector = :id")
    LiveData<InspectorEntity> getById(String id);

    @Query("SELECT * FROM inspectors WHERE emailInspector = :email AND passwordInspector = :password")
    LiveData<InspectorEntity> getByLogin(String email, String password);

    @Query("SELECT * FROM inspectors")
    LiveData<List<InspectorEntity>> getAll();

    @Insert
    void insert(InspectorEntity inspector);

    @Update
    void update(InspectorEntity inspector);

    @Delete
    void delete(InspectorEntity inspector);

    @Query("DELETE FROM inspectors")
    void deleteAll();

}

package com.example.equipmentinspection.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "inspections",
        foreignKeys = {
            @ForeignKey(
                entity = InspectorEntity.class,
                parentColumns = "idInspector",
                childColumns = "idInspectorInspection",
                onDelete = ForeignKey.CASCADE
            ),
            @ForeignKey(
                entity = EquipmentEntity.class,
                parentColumns = "idEquipment",
                childColumns = "idEquipmentInspection",
                onDelete = ForeignKey.CASCADE
            )
        },
        indices = {
            @Index(value = {"idInspectorInspection"}),
            @Index(value = {"idEquipmentInspection"})}
)

public class InspectionEntity {

    @PrimaryKey(autoGenerate = true)
    private int idInspection;

    private int idInspectorInspection;
    private int idEquipmentInspection;
    private String dateInspection;
    private String statusInspection;

    public int getIdInspection() {
        return idInspection;
    }

    public void setIdInspection(int idInspection) {
        this.idInspection = idInspection;
    }


    public int getIdInspectorInspection() {
        return idInspectorInspection;
    }

    public int getIdEquipmentInspection(){
        return idEquipmentInspection;
    }

    public void setIdInspectorInspection(int idInspectorInspection) {
        this.idInspectorInspection = idInspectorInspection;
    }

    public void setIdEquipmentInspection(int idEquipmentInspection) {
        this.idEquipmentInspection = idEquipmentInspection;
    }

    public String getDateInspection() {
        return dateInspection;
    }

    public void setDateInspection(String dateInspection) {
        this.dateInspection = dateInspection;
    }

    public String getStatusInspection() {
        return statusInspection;
    }

    public void setStatusInspection(String statusInspection) {
        this.statusInspection = statusInspection;
    }
}

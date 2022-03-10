package com.example.equipmentinspection.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "inspections", primaryKeys = {"idInspection"},
        foreignKeys = {
            @ForeignKey(
                entity = InspectorEntity.class,
                parentColumns = "idInspector",
                childColumns = "inspectorInspection",
                onDelete = ForeignKey.CASCADE
            ),
            @ForeignKey(
                entity = EquipmentEntity.class,
                parentColumns = "idEquipment",
                childColumns = "equipmentInspection",
                onDelete = ForeignKey.CASCADE
            )
        },
        indices = {
            @Index(value = {"inspectorInspection"}),
            @Index(value = {"equipmentInspection"})}
)

public class InspectionEntity {
    @PrimaryKey(autoGenerate = true)
    private int idInspection;
    private int equipmentInspection;
    private int inspectorInspection;
    private String dateInspection;
    private String statusInspection;

    public int getIdInspection() {
        return idInspection;
    }

    public void setIdInspection(int idInspection) {
        this.idInspection = idInspection;
    }

    public int getEquipmentInspection() {
        return equipmentInspection;
    }

    public void setEquipmentInspection(int equipmentInspection) {
        this.equipmentInspection = equipmentInspection;
    }

    public int getInspectorInspection() {
        return inspectorInspection;
    }

    public void setInspectorInspection(int inspectorInspection) {
        this.inspectorInspection = inspectorInspection;
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

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
            ),
                @ForeignKey(
                        entity = EquipmentEntity.class,
                        parentColumns = "nameEquipment",
                        childColumns = "nameEquipmentInspection",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
            @Index(value = {"idInspectorInspection"}),
            @Index(value = {"idEquipmentInspection"}),
            @Index(value = {"nameEquipmentInspection"})}
)

public class InspectionEntity {

    @PrimaryKey(autoGenerate = true)
    private Long idInspection;

    private Long idInspectorInspection;
    private Long idEquipmentInspection;
    private String nameEquipmentInspection;
    private String dateInspection;
    private String statusInspection;

    public String getNameEquipmentInspection() {
        return nameEquipmentInspection;
    }

    public void setNameEquipmentInspection(String nameEquipmentInspection) {
        this.nameEquipmentInspection = nameEquipmentInspection;
    }

    public Long getIdInspection() {
        return idInspection;
    }

    public void setIdInspection(Long idInspection) {
        this.idInspection = idInspection;
    }


    public Long getIdInspectorInspection() {
        return idInspectorInspection;
    }

    public Long getIdEquipmentInspection(){
        return idEquipmentInspection;
    }

    public void setIdInspectorInspection(Long idInspectorInspection) {
        this.idInspectorInspection = idInspectorInspection;
    }

    public void setIdEquipmentInspection(Long idEquipmentInspection) {
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof InspectionEntity)) return false;
        InspectionEntity o = (InspectionEntity) obj;
        return o.getIdInspection().equals(this.getIdInspection());
    }

    @Override
    public String toString(){
        return nameEquipmentInspection + " Inspection on " + dateInspection;
    }
}

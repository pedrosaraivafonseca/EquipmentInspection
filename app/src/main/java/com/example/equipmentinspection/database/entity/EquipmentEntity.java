package com.example.equipmentinspection.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.concurrent.atomic.AtomicInteger;

@Entity(tableName = "equipments", primaryKeys = {"idEquipment"})
public class EquipmentEntity {
    @PrimaryKey(autoGenerate = true)
    private int idEquipment;
    private String nameEquipment;
    private double priceEquipment;
    private String purchaseDateEquipment;
    private String warrantyDateEquipment;
    private String lastInspectionDateEquipment;
    private String nextInspectionDateEquipment;
    private InspectorEntity lastInspectorEquipment;
    private String statusEquipment;

    public int getIdEquipment() {
        return idEquipment;
    }

    public void setIdEquipment(int idEquipment) {
        this.idEquipment = idEquipment;
    }

    public String getNameEquipment() {
        return nameEquipment;
    }

    public void setNameEquipment(String nameEquipment) {
        this.nameEquipment = nameEquipment;
    }

    public double getPriceEquipment() {
        return priceEquipment;
    }

    public void setPriceEquipment(double priceEquipment) {
        this.priceEquipment = priceEquipment;
    }

    public String getPurchaseDateEquipment() {
        return purchaseDateEquipment;
    }

    public void setPurchaseDateEquipment(String purchaseDateEquipment) {
        this.purchaseDateEquipment = purchaseDateEquipment;
    }

    public String getWarrantyDateEquipment() {
        return warrantyDateEquipment;
    }

    public void setWarrantyDateEquipment(String warrantyDateEquipment) {
        this.warrantyDateEquipment = warrantyDateEquipment;
    }

    public String getLastInspectionDateEquipment() {
        return lastInspectionDateEquipment;
    }

    public void setLastInspectionDateEquipment(String lastInspectionDateEquipment) {
        this.lastInspectionDateEquipment = lastInspectionDateEquipment;
    }

    public String getNextInspectionDateEquipment() {
        return nextInspectionDateEquipment;
    }

    public void setNextInspectionDateEquipment(String nextInspectionDateEquipment) {
        this.nextInspectionDateEquipment = nextInspectionDateEquipment;
    }

    public InspectorEntity getLastInspectorEquipment() {
        return lastInspectorEquipment;
    }

    public void setLastInspectorEquipment(InspectorEntity lastInspectorEquipment) {
        this.lastInspectorEquipment = lastInspectorEquipment;
    }

    public String getStatusEquipment() {
        return statusEquipment;
    }

    public void setStatusEquipment(String statusEquipment) {
        this.statusEquipment = statusEquipment;
    }
}

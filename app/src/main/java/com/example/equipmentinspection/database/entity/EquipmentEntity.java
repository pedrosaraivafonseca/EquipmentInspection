package com.example.equipmentinspection.database.entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Entity(tableName = "equipments", indices = {@Index(value = {"nameEquipment"}, unique = true)})
public class EquipmentEntity {

    @PrimaryKey(autoGenerate = true)
    private Long idEquipment;

    private String nameEquipment;
    private double priceEquipment;
    private String purchaseDateEquipment;
    private String warrantyDateEquipment;
    private String lastInspectionDateEquipment;
    private String nextInspectionDateEquipment;
    private String lastInspectorEquipment;
    private String statusEquipment;

    public EquipmentEntity(){

    }

    //Fill the specified fields with generic info for next and last inspection as well as status
    public void setNewEquipmentFields(){
        setLastInspectionDateEquipment(null);
        setLastInspectorEquipment("");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 3);
        Date nextInspectionTime = cal.getTime();
        setNextInspectionDateEquipment(nextInspectionTime.toString());
        setStatusEquipment("Uninspected");
    }

    public Long getIdEquipment() {
        return idEquipment;
    }

    public void setIdEquipment(Long idEquipment) {
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

    public String getLastInspectorEquipment() {
        return lastInspectorEquipment;
    }

    public void setLastInspectorEquipment(String lastInspectorEquipment) {
        this.lastInspectorEquipment = lastInspectorEquipment;
    }

    public String getStatusEquipment() {
        return statusEquipment;
    }

    public void setStatusEquipment(String statusEquipment) {
        this.statusEquipment = statusEquipment;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof EquipmentEntity)) return false;
        EquipmentEntity o = (EquipmentEntity) obj;
        return o.getIdEquipment().equals(this.getIdEquipment());
    }

    @Override
    public String toString(){
        return nameEquipment;
    }
}

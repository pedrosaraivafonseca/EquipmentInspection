package com.example.equipmentinspection.database.entity;

import com.google.firebase.database.Exclude;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EquipmentEntity {

    private String id;
    private String name;
    private double price;
    private String purchaseDate;
    private String warrantyDate;
    private String lastInspectionDate;
    private String nextInspectionDate;
    private String lastInspector;
    private String status;

    public EquipmentEntity(){
    }

    //Fill the specified fields with generic info for next and last inspection as well as status
    public void setNewEquipmentFields(){
        setLastInspectionDate(null);
        setLastInspector("");
        Calendar cal = Calendar.getInstance();
        //cal.add(Calendar.MONTH, 3);
        //Date nextInspectionTime = cal.getTime();
        //setNextInspectionDate(nextInspectionTime.toString());
        setStatus("Uninspected");
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getWarrantyDate() {
        return warrantyDate;
    }

    public void setWarrantyDate(String warrantyDate) {
        this.warrantyDate = warrantyDate;
    }

    public String getLastInspectionDate() {
        return lastInspectionDate;
    }

    public void setLastInspectionDate(String lastInspectionDate) {
        this.lastInspectionDate = lastInspectionDate;
    }

    public String getNextInspectionDate() {
        return nextInspectionDate;
    }

    public void setNextInspectionDate(String nextInspectionDate) {
        this.nextInspectionDate = nextInspectionDate;
    }

    public String getLastInspector() {
        return lastInspector;
    }

    public void setLastInspector(String lastInspector) {
        this.lastInspector = lastInspector;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof EquipmentEntity)) return false;
        EquipmentEntity o = (EquipmentEntity) obj;
        return o.getId().equals(this.getId());
    }

    @Override
    public String toString(){
        return name;
    }

    @Exclude
    public Map<String, Object> map(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("price", price);
        result.put("purchaseDate", purchaseDate);
        result.put("warrantyDate", warrantyDate);
        result.put("lastInspection", lastInspectionDate);
        result.put("lastInspector", lastInspector);
        result.put("nextInspection", nextInspectionDate);
        result.put("status", status);

        return result;
    }

}

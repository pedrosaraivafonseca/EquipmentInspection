package com.example.equipmentinspection.database.entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;
public class InspectionEntity {
    private String id;
    private String idInspector;
    private String idEquipment;
    private String nameEquipment;
    private String date;
    private String status;

    public String getNameEquipment() {
        return nameEquipment;
    }

    public void setNameEquipment(String nameEquipment) {
        this.nameEquipment = nameEquipment;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdInspector() {
        return idInspector;
    }

    public String getIdEquipment(){
        return idEquipment;
    }

    public void setIdInspector(String idInspector) {
        this.idInspector = idInspector;
    }

    public void setIdEquipment(String idEquipment) {
        this.idEquipment = idEquipment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
        if (!(obj instanceof InspectionEntity)) return false;
        InspectionEntity o = (InspectionEntity) obj;
        return o.getId().equals(this.getId());
    }

    @Override
    public String toString(){
        return nameEquipment + " Inspection on " + date;
    }

    @Exclude
    public Map<String, Object> map(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("inspectorId", idInspector);
        result.put("equipmentId", idEquipment);
        result.put("nameEquipment", nameEquipment);
        result.put("date", date);
        result.put("status", status);

        return result;
    }
}

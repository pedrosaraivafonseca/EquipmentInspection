package com.example.equipmentinspection.database.entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;
public class InspectionEntity {
    private String idInspection;
    private String idInspectorInspection;
    private String idEquipmentInspection;
    private String nameEquipmentInspection;
    private String dateInspection;
    private String statusInspection;

    public String getNameEquipmentInspection() {
        return nameEquipmentInspection;
    }

    public void setNameEquipmentInspection(String nameEquipmentInspection) {
        this.nameEquipmentInspection = nameEquipmentInspection;
    }

    @Exclude
    public String getIdInspection() {
        return idInspection;
    }

    public void setIdInspection(String idInspection) {
        this.idInspection = idInspection;
    }

    public String getIdInspectorInspection() {
        return idInspectorInspection;
    }

    public String getIdEquipmentInspection(){
        return idEquipmentInspection;
    }

    public void setIdInspectorInspection(String idInspectorInspection) {
        this.idInspectorInspection = idInspectorInspection;
    }

    public void setIdEquipmentInspection(String idEquipmentInspection) {
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

    @Exclude
    public Map<String, Object> map(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("inspectorId", idInspectorInspection);
        result.put("equipmentId", idEquipmentInspection);
        result.put("nameEquipment", nameEquipmentInspection);
        result.put("date", dateInspection);
        result.put("status", statusInspection);

        return result;
    }
}

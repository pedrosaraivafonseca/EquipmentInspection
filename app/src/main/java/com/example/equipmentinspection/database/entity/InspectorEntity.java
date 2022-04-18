package com.example.equipmentinspection.database.entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class InspectorEntity {
    private String idInspector;
    private String nameInspector;
    private String firstNameInspector;
    private String emailInspector;
    private String passwordInspector;

    public InspectorEntity(String nameInspector, String firstNameInspector, String emailInspector, String passwordInspector) {
        this.nameInspector = nameInspector;
        this.firstNameInspector = firstNameInspector;
        this.emailInspector = emailInspector;
        this.passwordInspector = passwordInspector;
    }

    @Exclude
    public String getIdInspector() {
        return idInspector;
    }

    public void setIdInspector(String idInspector) {
        this.idInspector = idInspector;
    }

    public String getNameInspector() {
        return nameInspector;
    }

    public void setNameInspector(String nameInspector) {
        this.nameInspector = nameInspector;
    }

    public String getFirstNameInspector() {
        return firstNameInspector;
    }

    public void setFirstNameInspector(String firstNameInspector) {
        this.firstNameInspector = firstNameInspector;
    }

    public String getEmailInspector() {
        return emailInspector;
    }

    public void setEmailInspector(String emailInspector) {
        this.emailInspector = emailInspector;
    }

    public String getPasswordInspector() {
        return passwordInspector;
    }

    public void setPasswordInspector(String passwordInspector) {
        this.passwordInspector = passwordInspector;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof InspectorEntity)) return false;
        InspectorEntity o = (InspectorEntity) obj;
        return o.getIdInspector().equals(this.getIdInspector());
    }

    @Override
    public String toString(){
        return firstNameInspector + " " + nameInspector;
    }

    @Exclude
    public Map<String, Object> map(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", emailInspector);
        result.put("password", passwordInspector);
        result.put("firstName", firstNameInspector);
        result.put("lastName", nameInspector);

        return result;
    }
}

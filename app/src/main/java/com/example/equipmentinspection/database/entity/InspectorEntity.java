package com.example.equipmentinspection.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.concurrent.atomic.AtomicInteger;

@Entity(tableName = "inspectors")
public class InspectorEntity {

    @PrimaryKey(autoGenerate = true)
    private int idInspector;

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

    public int getIdInspector() {
        return idInspector;
    }

    public void setIdInspector(int idInspector) {
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
}

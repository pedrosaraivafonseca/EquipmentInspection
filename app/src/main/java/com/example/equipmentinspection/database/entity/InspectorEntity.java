package com.example.equipmentinspection.database.entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class InspectorEntity {
    private String id;
    private String lastName;
    private String firstName;
    private String email;
    private String password;

    public InspectorEntity(){}

    public InspectorEntity(String lastName, String firstName, String email, String password) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof InspectorEntity)) return false;
        InspectorEntity o = (InspectorEntity) obj;
        return o.getId().equals(this.getId());
    }

    @Override
    public String toString(){
        return firstName + " " + lastName;
    }

    @Exclude
    public Map<String, Object> map(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("password", password);
        result.put("firstName", firstName);
        result.put("lastName", lastName);

        return result;
    }
}

package com.amarpatel.eventmanagement;

public class Model {
    public String name,description,address,date,time,capacity;
    public int contact;

    public Model(String name, String capacity, String address, String date, String time) {
        this.name = name;
        this.address = address;
        this.date = date;
        this.time = time;
        this.capacity = capacity;
    }

    public Model() {}

    public Model setName(String name) {
        this.name = name;
        return null;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Model(String name, String address, String capacity) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
    }

    public Model setAddress(String address) {
        this.address = address;
        return null;
    }

    public Model setCapacity(String capacity) {
        this.capacity = capacity;
        return null;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    public Model(String name, String description, String address, String capacity, int contact) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.capacity = capacity;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public String getCapacity() {
        return capacity;
    }

    public int getContact() {
        return contact;
    }
}

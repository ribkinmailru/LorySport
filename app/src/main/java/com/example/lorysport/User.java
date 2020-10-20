package com.example.lorysport;

import java.util.ArrayList;

public class User{

    public String name;
    public String number;
    ArrayList<String> contacts;

    public ArrayList<String> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<String> contacts) {
        this.contacts = contacts;
    }

    public User(){

    }

    public User(String name, String number) {
        this.name = name;
        this.number = number;
        contacts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


}

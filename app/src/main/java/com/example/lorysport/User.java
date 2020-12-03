package com.example.lorysport;


import java.util.ArrayList;

public class User {

    public String name;
    public String number;
    public ArrayList<String> contacts;
    public ArrayList<String> photos;
    public String avatar;
    public String status;

    public void setContacts(ArrayList<String> contacts) {
        this.contacts = contacts;
    }

    public void addcontact(String contact){
        if(this.contacts==null){
            this.contacts = new ArrayList<>();
        }
        contacts.add(contact);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<String> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public ArrayList<String> getContacts() {
        return contacts;
    }

    public User(){
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

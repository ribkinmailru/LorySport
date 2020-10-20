package com.example.lorysport;

import io.realm.RealmObject;

public class Exercise extends RealmObject {

    String name;
    String image;


    public Exercise(String name, String image) {
        this.name = name;
        this.image = image;
    }
    public Exercise(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }







}

package com.example.lorysport;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Body extends RealmObject {

    @PrimaryKey
    long time;
    RealmList<Double> params;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }


    public RealmList<Double> getParams() {
        return params;
    }

    public void setParams(RealmList<Double> params) {
        this.params = params;
    }

    public Body(long time) {
        this.time = time;
        }

    public Body(){

    }



}

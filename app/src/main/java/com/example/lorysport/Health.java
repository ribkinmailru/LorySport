package com.example.lorysport;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Health extends RealmObject {
    @PrimaryKey
    long time;
    RealmList<Double> params;

    public Health(long time) {
        this.time = time;
    }
    public Health(){

    }

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

}

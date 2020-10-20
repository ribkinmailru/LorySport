package com.example.lorysport;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Train extends RealmObject {

    @PrimaryKey
    String time;
    public RealmList<Exercise> exe;

    public Train() {
    }

    public Train(String time) {
        this.time = time;
        exe = new RealmList<>();
    }


    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public RealmList<Exercise> getExe() {
        return exe;
    }
    public void setExe(RealmList<Exercise> exe) {
        this.exe = exe;
    }
    public void addexe(Exercise ee){
        exe.add(ee);
    }


}

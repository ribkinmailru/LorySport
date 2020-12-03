package com.example.lorysport;

import java.util.Objects;

public class Message implements Comparable<Message> {
    public String author;
    public String reciever;
    public String text;
    public long time;
    public boolean photo;
    public boolean readed;
    public boolean exercize;

    public Message(){

    }

    public Message(String author,String reciever, long time, String text, boolean photo, boolean exercize){
        this.reciever = reciever;
        this.author = author;
        this.time = time;
        this.text = text;
        this.photo = photo;
        this.exercize = exercize;
    }

    @Override
    public int compareTo(Message o) {
        return Long.compare(time, o.time);
    }

    @Override
    public boolean equals(Object o) {
        if (getClass() != o.getClass()) {
            return false;
        }
        Message m = (Message)o;
        return m.author.equals(author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author);
    }
}

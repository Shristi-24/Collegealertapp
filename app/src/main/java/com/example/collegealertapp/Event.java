package com.example.collegealertapp;

public class Event {
    private String title, description, date;
    public Event() { }
    public Event(String t, String d, String dt) {
        this.title = t;
        this.description = d;
        this.date = dt;
    }
    public String getTitle()       { return title; }
    public String getDescription() { return description; }
    public String getDate()        { return date; }
}

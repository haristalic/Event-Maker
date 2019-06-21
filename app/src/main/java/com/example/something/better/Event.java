package com.example.something.better;

import java.util.ArrayList;


public class Event {

    String date;
    String description;
    String email;
    String eventName;
    String imageURL;
    String key;
    String numInterested;
    ArrayList<String> peopleInterested;

    String address;

    public Event() {
        peopleInterested = new ArrayList<>();

    }
}

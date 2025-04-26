package com.quest.service.model;

public class Option {
    private final String id;
    private final String text;
    private final String nextID;

    public Option(String id, String text, String nextId){
        this.id = id;
        this.text = text;
        this.nextID = nextId;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getNextID() {
        return nextID;
    }
}

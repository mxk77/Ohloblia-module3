package com.quest.service.model;

public class Option {
    private final String id;
    private final String text; // текст варіанту відповіді
    private final String nextID; // до якого питання чи результату веде цей вибір

    public Option(String id, String text, String nextID){
        this.id = id;
        this.text = text;
        this.nextID = nextID;
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

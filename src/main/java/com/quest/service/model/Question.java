package com.quest.service.model;

import java.util.List;

public class Question {
    private final String id;
    private final String text;
    private final List<Option> options;

    public Question(String id, String text, List<Option> options){
        this.id = id;
        this.text = text;
        this.options = options;
    }

    public List<Option> getOptions() {
        return options;
    }

    public String getText() {
        return text;
    }

    public String getId() {
        return id;
    }
}

package com.example.finalyearproject.Models;

public class Report {
    public String input;
    public String suggestion;
    public String description;

    public Report() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Report(String input, String suggestion, String description) {
        this.input = input;
        this.suggestion = suggestion;
        this.description = description;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

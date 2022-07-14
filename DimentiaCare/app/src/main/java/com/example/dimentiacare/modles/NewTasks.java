package com.example.dimentiacare.modles;

public class NewTasks {

    String topic,details,id;

    public NewTasks() {
    }

    public NewTasks(String topic, String details, String id) {
        this.topic = topic;
        this.details = details;
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

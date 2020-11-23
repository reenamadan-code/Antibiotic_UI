package com.example.testgage.Model;

public class Metric {
    String metricId;
    String datetime;
    String heart_rate;

    public String get_metricId() {
        return metricId;
    }

    public void set_metricId(String email) {
        this.metricId = email;
    }

    public String get_heart_rate() {
        return heart_rate;
    }

    public void set_heart_rate(String heart_rate) {
        this.heart_rate = heart_rate;
    }

    public String get_datetime() {
        return datetime;
    }

    public void set_datetime(String datetime) {
        this.datetime = datetime;
    }
}

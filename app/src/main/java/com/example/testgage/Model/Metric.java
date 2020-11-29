package com.example.testgage.Model;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Metric {
    String metricId;
    String datetime;
    String heart_rate;

    public String get_metricId() {
        return metricId;
    }

    public void set_metricId(String metricId) {
        this.metricId = metricId;
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

    public String toString() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.US);
        return String.format("Time: %S Heart Rate: %S", this.datetime, this.heart_rate);
    }
}

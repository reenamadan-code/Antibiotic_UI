package com.example.testgage.Model;

import java.util.Date;

public class Metric {
    String email;
    String deviceId;
    String datetime;
    String heart_rate;

    public String get_email() {
        return email;
    }

    public void set_email(String email) {
        this.email = email;
    }

    public String get_device_id() {
        return deviceId;
    }

    public void set_device_id(String deviceId) {
        this.deviceId = deviceId;
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

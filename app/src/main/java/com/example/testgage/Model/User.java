package com.example.testgage.Model;

public class User {
    String email;
    String first_name;
    String last_name;
    String gender;
    String age;
    String deviceId;


    public String get_email() {
        return email;
    }

    public void set_email(String email) {
        this.email = email;
    }

    public String get_first_name() {
        return first_name;
    }

    public void set_first_name(String first_name) {
        this.first_name = first_name;
    }

    public String get_last_name() {
        return last_name;
    }

    public void set_last_name(String last_name) {
        this.last_name = last_name;
    }

    public String get_gender() {
        return gender;
    }

    public void set_gender(String gender) {
        this.gender = gender;
    }

    public String get_age() {
        return age;
    }

    public void set_age(String age) {
        this.age = age;
    }

    public String get_deviceId() {
        return deviceId;
    }

    public void set_deviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}

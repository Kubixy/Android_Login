package com.example.login;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class User implements Parcelable {

    private String email, password, username, phone;
    private Boolean terms, spam;
    private ArrayList<String> tasks = new ArrayList<>();

    public User() {}

    protected User(Parcel in) {
        email = in.readString();
        password = in.readString();
        username = in.readString();
        phone = in.readString();
        byte tmpTerms = in.readByte();
        terms = tmpTerms == 0 ? null : tmpTerms == 1;
        byte tmpSpam = in.readByte();
        spam = tmpSpam == 0 ? null : tmpSpam == 1;
        tasks = in.createStringArrayList();
    }

    public ArrayList<String> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<String> tasks) {
        this.tasks = tasks;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(username);
        parcel.writeString(phone);
        parcel.writeByte((byte) (terms == null ? 0 : terms ? 1 : 2));
        parcel.writeByte((byte) (spam == null ? 0 : spam ? 1 : 2));
        parcel.writeStringList(tasks);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getTerms() {
        return terms;
    }

    public void setTerms(Boolean terms) {
        this.terms = terms;
    }

    public Boolean getSpam() {
        return spam;
    }

    public void setSpam(Boolean spam) {
        this.spam = spam;
    }
}
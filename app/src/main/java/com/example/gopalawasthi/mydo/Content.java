package com.example.gopalawasthi.mydo;

/**
 * Created by Gopal Awasthi on 20-02-2018.
 */

public class Content implements Item {
  private String name;
  private String date;
  private String time;
 private long timestamp;
 private int id=0;
 private boolean check =false;


    public Content(String name, String date, String time, long timestamp ) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.timestamp = timestamp;

    }

    public Content(String name, String date, String time, long timestamp, int id) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.timestamp = timestamp;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public boolean isHeader() {
        return false;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }


}

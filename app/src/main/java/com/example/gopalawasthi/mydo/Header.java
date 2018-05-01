package com.example.gopalawasthi.mydo;

/**
 * Created by Gopal Awasthi on 20-02-2018.
 */

public class Header implements Item {

   private String header;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private  int id;

    public Header(String header) {
        this.id =-1;
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    @Override
    public boolean isHeader() {
        return true;
    }


    }


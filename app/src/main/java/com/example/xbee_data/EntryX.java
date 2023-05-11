package com.example.xbee_data;

import com.github.mikephil.charting.data.Entry;

import java.util.Date;

public class EntryX extends Entry {
    private Float field1;
    private Date created_at;
    // Add more fields as needed
    public EntryX() {
        super();
    }
    public EntryX(long timestamp, float value) {
        super(timestamp, value);
        this.created_at = new Date(timestamp);
    }

    public Float getField1() {
        return field1;
    }

    public void setField1(Float field1) {
        this.field1 = field1;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void SetCreated_at(Date field2) {
        this.created_at = field2;
    }

    // Add getters and setters for additional fields
}


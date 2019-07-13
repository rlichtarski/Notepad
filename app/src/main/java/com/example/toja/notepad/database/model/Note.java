package com.example.toja.notepad.database.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes_table")
public class Note  {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String note;
    private String date;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public String getDate() {
        return date;
    }

    public Note(String note, String date) {
        this.note = note;
        this.date = date;
    }
}

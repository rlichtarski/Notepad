package com.example.toja.notepad.database.model;

public class Note {

    public static final String TABLE_NAME = "notes_table";

    public static final String COL_ID = "ID";
    public static final String COL_NOTE = "NOTE";
    public static final String COL_DATE = "DATE";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_NOTE + " TEXT,"
            + COL_DATE + " TEXT"
            + ");";
}

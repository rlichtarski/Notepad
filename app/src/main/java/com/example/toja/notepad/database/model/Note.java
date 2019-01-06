package com.example.toja.notepad.database.model;

import android.provider.BaseColumns;

public class Note implements BaseColumns {

    public static final String TABLE_NAME = "notes_table";

    public static final String COL_NOTE = "NOTE";
    public static final String COL_DATE = "DATE";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_NOTE + " TEXT,"
            + COL_DATE + " TEXT"
            + ");";
}

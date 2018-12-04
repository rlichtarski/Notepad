package com.example.toja.notepad;

public class Note {

    public static final String TABLE_NAME = "notes_table";

    public static final String COL_ID = "ID";
    public static final String COL_NOTE = "NOTE";
    public static final String COL_DATE = "DATE";

    private int mId;
    private String mNote;
    private String mDate;

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_NOTE + " TEXT,"
            + COL_DATE + " TEXT"
            + ")";

    public Note(int id, String note, String date) {
        this.mId = id;
        this.mNote = note;
        this.mDate = date;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String mNote) {
        this.mNote = mNote;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }


}

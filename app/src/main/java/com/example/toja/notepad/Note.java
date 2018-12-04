package com.example.toja.notepad;

public class Note {

    public static final String TABLE_NAME = "notes_table";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "NOTE";
    public static final String COL_3 = "DATE";

    private int mId;
    private String mNote;
    private String mDate;

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_2 + " TEXT,"
            + COL_3 + " TEXT"
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

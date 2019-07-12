package com.example.toja.notepad;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.toja.notepad.database.model.Note;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;

    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);
        noteDao = noteDatabase.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public void insert(Note note) {
        new InsertUpdateDeleteAsyncTask(noteDao, "insert").execute(note);
    }

    public void update(Note note) {
        new InsertUpdateDeleteAsyncTask(noteDao, "update").execute(note);
    }

    public void delete(Note note) {
        new InsertUpdateDeleteAsyncTask(noteDao, "delete").execute(note);
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    private static class InsertUpdateDeleteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;
        private String methodType;

        public InsertUpdateDeleteAsyncTask(NoteDao noteDao, String methodType) {
            this.noteDao = noteDao;
            this.methodType = methodType;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            switch (methodType) {
                case "insert":
                    noteDao.insert(notes[0]);
                    break;
                case "update":
                    noteDao.update(notes[0]);
                    break;
                case "delete":
                    noteDao.delete(notes[0]);
                    break;
            }

            return null;
        }
    }

}

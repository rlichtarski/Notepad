package com.example.toja.notepad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toja.notepad.database.model.Note;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.NoteRecyclerViewHolder>{
    private Context mContext;
    private List<Note> notesList = new ArrayList<>();
    private View.OnClickListener itemClickListener;

    public RecyclerViewAdapter(Context context) {
        mContext = context;
    }

    public class NoteRecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView noteText, dateView;

        public NoteRecyclerViewHolder(View itemView) {
            super(itemView);
            noteText = itemView.findViewById(R.id.note);
            dateView = itemView.findViewById(R.id.date);
            itemView.setTag(this);
            itemView.setOnClickListener(itemClickListener);
        }
    }

    public void setItemClickListener(View.OnClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public NoteRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.note_item, parent, false);
        return new NoteRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteRecyclerViewHolder holder,int position) {
        final Note note = notesList.get(position);
        holder.noteText.setText(note.getNote());
        holder.dateView.setText(note.getDate());
    }

    public void setNotes(List<Note> notes) {
        notesList = notes;
        notifyDataSetChanged();
    }

    public Note getNoteAt(int position) {
        return notesList.get(position);
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

}

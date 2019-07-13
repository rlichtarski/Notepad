package com.example.toja.notepad;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toja.notepad.database.model.Note;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteViewHolder> {
    private View.OnClickListener itemClickListener;

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem,@NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem,@NonNull Note newItem) {
            return oldItem.getNote().equals(newItem.getNote()) &&
                    oldItem.getDate().equals(newItem.getDate());
        }
    };

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView noteText, dateView;

        public NoteViewHolder(View itemView) {
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
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder,int position) {
        final Note note = getItem(position);
        holder.noteText.setText(note.getNote());
        holder.dateView.setText(note.getDate());
    }

    public Note getNoteAt(int position) {
        return getItem(position);
    }
}

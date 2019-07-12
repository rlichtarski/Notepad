package com.example.toja.notepad;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toja.notepad.database.model.Note;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.NoteRecyclerViewHolder>{
    private Context mContext;
    private List<Note> notesList = new ArrayList<>();


    public RecyclerViewAdapter(Context context) {
        mContext = context;
    }

    public class NoteRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView noteText, dateView;
        private ItemClickListener itemClickListener;

        public NoteRecyclerViewHolder(View itemView) {
            super(itemView);
            noteText = itemView.findViewById(R.id.note);
            dateView = itemView.findViewById(R.id.date);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition());
        }
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
        Note note = notesList.get(position);
        holder.noteText.setText(note.getNote());
        holder.dateView.setText(note.getDate().toString());


       /* holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view,int position) {
                showDialog(note, date);
            }
        });*/

    }

    public void setNotes(List<Note> notes) {
        notesList = notes;
        notifyDataSetChanged();
    }

    private void showDialog(String note, String date) {
        AppCompatActivity activity = (AppCompatActivity) mContext;                //need the context
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        NoteDialogFragment noteDialogFragment = NoteDialogFragment.newInstance(note, date);
        noteDialogFragment.show(fragmentTransaction, "fragmentTransaction");
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

}

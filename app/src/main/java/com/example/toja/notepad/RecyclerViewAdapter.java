package com.example.toja.notepad;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.NoteRecyclerViewHolder>{

    private Context mContext;
    private Cursor mCursor;

    public RecyclerViewAdapter() {}

    public RecyclerViewAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    public class NoteRecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView noteText;

        public NoteRecyclerViewHolder(View itemView) {
            super(itemView);
            noteText = itemView.findViewById(R.id.note);
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
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        String note = mCursor.getString(mCursor.getColumnIndex(Note.COL_NOTE));
        holder.noteText.setText(note);
    }


    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if(mCursor != null) {
            mCursor.close();
        }

        mCursor = newCursor;

        if(newCursor != null) {
            notifyDataSetChanged();
        }
    }
}

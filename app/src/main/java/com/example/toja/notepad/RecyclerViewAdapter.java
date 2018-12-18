package com.example.toja.notepad;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toja.notepad.database.model.Note;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.NoteRecyclerViewHolder>{

    private Context mContext;
    private Cursor mCursor;

    public RecyclerViewAdapter() {}

    public RecyclerViewAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    public class NoteRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView noteText;
        private TextView dateView;
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
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        final String note = mCursor.getString(mCursor.getColumnIndex(Note.COL_NOTE));
        holder.noteText.setText(note);

        String date = mCursor.getString(mCursor.getColumnIndex(Note.COL_DATE));
        holder.dateView.setText(date);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view,int position) {
                showToast("Position: " + position);
                position = position + 1;
                showDialog(position, note);
            }
        });

    }

    private void showDialog(int position, String note) {
        AppCompatActivity activity = (AppCompatActivity) mContext;                //need the context
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        NoteDialogFragment noteDialogFragment = NoteDialogFragment.newInstance(position, note);
        noteDialogFragment.show(fragmentTransaction, "fragmentTransaction");
    }

    private void showToast(String position) {
        Toast.makeText(mContext,position, Toast.LENGTH_SHORT).show();
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

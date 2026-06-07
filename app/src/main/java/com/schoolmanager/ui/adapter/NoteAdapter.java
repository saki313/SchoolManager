package com.schoolmanager.ui.adapter;

import com.schoolmanager.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import com.schoolmanager.data.entity.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private List<Note> notes = new ArrayList<>();
    private OnEditClickListener editListener;
    private OnDeleteClickListener deleteListener;

    public interface OnEditClickListener { void onEdit(Note note); }
    public interface OnDeleteClickListener { void onDelete(Note note); }

    public void setNotes(List<Note> list) {
        this.notes = list;
        notifyDataSetChanged();
    }

    public void setOnEditClickListener(OnEditClickListener listener) { this.editListener = listener; }
    public void setOnDeleteClickListener(OnDeleteClickListener listener) { this.deleteListener = listener; }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.tvMatiere.setText(note.getMatiere());
        holder.tvNote.setText(String.valueOf(note.getValeur()));
        if (note.getValeur() < 10)
            holder.tvNote.setTextColor(holder.itemView.getContext().getColor(R.color.red));
        else
            holder.tvNote.setTextColor(holder.itemView.getContext().getColor(R.color.green));

        holder.btnEdit.setOnClickListener(v -> { if (editListener != null) editListener.onEdit(note); });
        holder.btnDelete.setOnClickListener(v -> { if (deleteListener != null) deleteListener.onDelete(note); });
    }

    @Override public int getItemCount() { return notes.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMatiere, tvNote;
        Button btnEdit, btnDelete;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMatiere = itemView.findViewById(R.id.tvMatiere);
            tvNote = itemView.findViewById(R.id.tvNote);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
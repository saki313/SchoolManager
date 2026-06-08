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

import com.schoolmanager.data.database.AppDataBase;
import com.schoolmanager.data.entity.Cours;
import com.schoolmanager.data.entity.Note;
import com.schoolmanager.data.entity.NoteWithCours;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private List<NoteWithCours> notesWithCoursList = new ArrayList<>();
    private OnEditClickListener editListener;
    private OnDeleteClickListener deleteListener;

    public interface OnEditClickListener { void onEdit(Note note); }
    public interface OnDeleteClickListener { void onDelete(Note note); }

    public void setNotes(List<NoteWithCours> list) {
        this.notesWithCoursList = list;
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
        NoteWithCours currentItem = notesWithCoursList.get(position);
        // Récupérer le nom du cours via l'ID
        Note note = currentItem.note;
        Cours cours = currentItem.cours;

        if (cours != null) {
            holder.tvMatiere.setText(cours.getMatiere());
        } else {
            holder.tvMatiere.setText(note.getMatiere()); // Repli si le cours est nul
        }
        holder.tvNote.setText(String.valueOf(note.getValeur()));
        if (note.getValeur() < 10)
            holder.tvNote.setTextColor(holder.itemView.getContext().getColor(R.color.red));
        else
            holder.tvNote.setTextColor(holder.itemView.getContext().getColor(R.color.green));

        holder.btnEdit.setOnClickListener(v -> { if (editListener != null) editListener.onEdit(note); });
        holder.btnDelete.setOnClickListener(v -> { if (deleteListener != null) deleteListener.onDelete(note); });
    }

    @Override public int getItemCount() { return notesWithCoursList.size(); }

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
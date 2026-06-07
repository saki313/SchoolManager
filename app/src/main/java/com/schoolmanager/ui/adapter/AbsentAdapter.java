package com.schoolmanager.ui.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.schoolmanager.data.entity.Presence;

public class AbsentAdapter extends RecyclerView.Adapter<AbsentAdapter.ViewHolder> {

    private List<Presence> absents;

    public AbsentAdapter(List<Presence> absents) {
        this.absents = absents;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Presence p = absents.get(position);
        // Ici vous devez récupérer le nom de l'étudiant à partir de son id
        // Pour simplifier, on affiche juste l'id et la date. Idéalement, faites une requête pour le nom.
        holder.text1.setText("Étudiant ID: " + p.getIdEtudiant());
        holder.text2.setText("Date: " + p.getDate());
    }

    @Override
    public int getItemCount() {
        return absents.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text1, text2;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(android.R.id.text1);
            text2 = itemView.findViewById(android.R.id.text2);
        }
    }
}

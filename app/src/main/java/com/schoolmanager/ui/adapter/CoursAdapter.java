package com.schoolmanager.ui.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.schoolmanager.R;

import java.util.List;

import com.schoolmanager.data.entity.Cours;

public class CoursAdapter extends RecyclerView.Adapter<CoursAdapter.ViewHolder> {
    private List<Cours> cours;

    public CoursAdapter(List<Cours> cours) { this.cours = cours; }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cours, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cours c = cours.get(position);
        holder.tvMatiere.setText(c.getMatiere());
        holder.tvHeure.setText(c.getHeureDebut() + " - " + c.getHeureFin());
        holder.tvSalle.setText("Salle : " + c.getSalle());
    }

    @Override public int getItemCount() { return cours.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMatiere, tvHeure, tvSalle;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMatiere = itemView.findViewById(R.id.tvMatiere);
            tvHeure = itemView.findViewById(R.id.tvHeure);
            tvSalle = itemView.findViewById(R.id.tvSalle);
        }
    }
}

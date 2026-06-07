package com.schoolmanager.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.schoolmanager.R;

import java.util.List;

import com.schoolmanager.data.entity.Etudiant;

public class PresenceAdapter extends RecyclerView.Adapter<PresenceAdapter.ViewHolder> {
    private List<Etudiant> etudiants;

    public PresenceAdapter(List<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_presence, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Etudiant e = etudiants.get(position);
        holder.tvNom.setText(e.getNom() + " " + e.getPrenom());
        holder.cbPresent.setChecked(true); // par défaut présent
    }

    @Override
    public int getItemCount() {
        return etudiants.size();
    }

    public int getEtudiantId(int position) {
        return etudiants.get(position).getId();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNom;
        private CheckBox cbPresent;  // privé

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNom = itemView.findViewById(R.id.tvNom);
            cbPresent = itemView.findViewById(R.id.cbPresent);
        }

        // Getter public pour accéder à l'état du CheckBox
        public boolean isPresent() {
            return cbPresent.isChecked();
        }
    }
}
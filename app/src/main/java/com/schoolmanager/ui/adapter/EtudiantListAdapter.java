package com.schoolmanager.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.schoolmanager.R;
import com.schoolmanager.data.entity.Etudiant;
import java.util.ArrayList;
import java.util.List;

public class EtudiantListAdapter extends RecyclerView.Adapter<EtudiantListAdapter.ViewHolder> {

    private List<Etudiant> etudiants = new ArrayList<>();
    private OnItemClickListener clickListener;
    private OnDeleteClickListener deleteListener;

    public interface OnItemClickListener {
        void onItemClick(Etudiant etudiant);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(Etudiant etudiant);
    }

    public void setEtudiants(List<Etudiant> list) {
        this.etudiants = list != null ? list : new ArrayList<>();
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        try {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_etudiant, parent, false);
            return new ViewHolder(view);
        } catch (Exception e) {
            // Fallback en cas d'erreur
            View view = new View(parent.getContext());
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            Etudiant etudiant = etudiants.get(position);
            holder.tvNomPrenom.setText(etudiant.getNom() + " " + etudiant.getPrenom());
            holder.tvMatricule.setText(etudiant.getMatricule());
            holder.tvMoyenne.setText("Moyenne: --");

            if (etudiant.getPhotoPath() != null && !etudiant.getPhotoPath().isEmpty()) {
                // Photo chargée plus tard
            } else {
                holder.ivPhoto.setImageResource(android.R.drawable.ic_menu_gallery);
            }

            holder.itemView.setOnClickListener(v -> {
                if (clickListener != null) clickListener.onItemClick(etudiant);
            });

            holder.btnDelete.setOnClickListener(v -> {
                if (deleteListener != null) deleteListener.onDeleteClick(etudiant);
            });
        } catch (Exception e) {
            // Silencieux
        }
    }

    @Override
    public int getItemCount() {
        return etudiants.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPhoto;
        TextView tvNomPrenom, tvMatricule, tvMoyenne;
        ImageButton btnDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            tvNomPrenom = itemView.findViewById(R.id.tvNomPrenom);
            tvMatricule = itemView.findViewById(R.id.tvMatricule);
            tvMoyenne = itemView.findViewById(R.id.tvMoyenne);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
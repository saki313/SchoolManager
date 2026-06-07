package com.schoolmanager.ui.etudiant;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.schoolmanager.R;

import androidx.annotation.NonNull;

import java.util.List;

import com.schoolmanager.data.entity.Presence;
import com.schoolmanager.viewmodel.EtudiantViewModel;
import com.schoolmanager.viewmodel.PresenceViewModel;

public class HistoriqueEtudiantActivity extends AppCompatActivity {
    private int etudiantId;
    private PresenceViewModel presenceVM;
    private EtudiantViewModel etudiantVM;
    private TextView tvInfo, tvNbAbsences;
    private RecyclerView recyclerView;
    private PresenceHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique_etudiant);

        etudiantId = getIntent().getIntExtra("etudiant_id", -1);
        presenceVM = new ViewModelProvider(this).get(PresenceViewModel.class);
        etudiantVM = new ViewModelProvider(this).get(EtudiantViewModel.class);

        tvInfo = findViewById(R.id.tvInfo);
        tvNbAbsences = findViewById(R.id.tvNbAbsences);
        recyclerView = findViewById(R.id.recyclerViewHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        etudiantVM.getEtudiantById(etudiantId).observe(this, etudiant -> {
            if (etudiant != null) tvInfo.setText(etudiant.getNom() + " " + etudiant.getPrenom());
        });

        presenceVM.getNbAbsences(etudiantId).observe(this, nb -> {
            tvNbAbsences.setText("Nombre d'absences : " + nb);
        });

        presenceVM.getPresencesByEtudiant(etudiantId).observe(this, presences -> {
            adapter = new PresenceHistoryAdapter(presences);
            recyclerView.setAdapter(adapter);
        });
    }

    class PresenceHistoryAdapter extends RecyclerView.Adapter<PresenceHistoryAdapter.ViewHolder> {
        private List<Presence> list;
        PresenceHistoryAdapter(List<Presence> list) { this.list = list; }
        @NonNull @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
            return new ViewHolder(v);
        }
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Presence p = list.get(position);
            holder.text1.setText(p.getDate());
            holder.text2.setText(p.isPresent() ? "Présent" : "Absent");
        }
        @Override public int getItemCount() { return list.size(); }
        class ViewHolder extends RecyclerView.ViewHolder {
            TextView text1, text2;
            ViewHolder(View v) { super(v); text1 = v.findViewById(android.R.id.text1); text2 = v.findViewById(android.R.id.text2); }
        }
    }
}
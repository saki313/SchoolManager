package com.schoolmanager.ui.presence;


import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.schoolmanager.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.schoolmanager.data.entity.Presence;
import com.schoolmanager.ui.adapter.PresenceAdapter;
import com.schoolmanager.viewmodel.EtudiantViewModel;
import com.schoolmanager.viewmodel.PresenceViewModel;

public class PrisePresenceActivity extends AppCompatActivity {
    private PresenceViewModel presenceVM;
    private EtudiantViewModel etudiantVM;
    private PresenceAdapter adapter;
    private String currentDate;
    private TextView tvDate;
    private Button btnSave;
    private RecyclerView recyclerView;   // AJOUTÉ : attribut de classe

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prise_presence);

        presenceVM = new ViewModelProvider(this).get(PresenceViewModel.class);
        etudiantVM = new ViewModelProvider(this).get(EtudiantViewModel.class);

        tvDate = findViewById(R.id.tvDate);
        btnSave = findViewById(R.id.btnSave);
        recyclerView = findViewById(R.id.recyclerViewPresences);   // MODIFIÉ : plus de déclaration locale
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        tvDate.setText("Date : " + currentDate);

        etudiantVM.getAllEtudiants().observe(this, etudiants -> {
            adapter = new PresenceAdapter(etudiants);
            recyclerView.setAdapter(adapter);
        });

        btnSave.setOnClickListener(v -> savePresences());
    }

    private void savePresences() {
        List<Presence> presences = new ArrayList<>();
        for (int i = 0; i < adapter.getItemCount(); i++) {
            PresenceAdapter.ViewHolder holder = (PresenceAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
            if (holder != null) {
                int idEtudiant = adapter.getEtudiantId(i);
                boolean present = holder.isPresent();   // ← plus d'erreur
                presences.add(new Presence(idEtudiant, currentDate, present));
            }
        }
        presenceVM.savePresences(currentDate, presences, () -> {
            runOnUiThread(() -> {
                Toast.makeText(this, "Présences enregistrées", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }
}
package com.schoolmanager.ui.emploi;


import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.schoolmanager.R;

import java.util.ArrayList;
import java.util.List;

import com.schoolmanager.data.entity.Presence;
import com.schoolmanager.ui.adapter.AbsentAdapter;
import com.schoolmanager.viewmodel.PresenceViewModel;

public class HistoriqueParDateActivity extends AppCompatActivity {
    private String date;
    private PresenceViewModel presenceVM;
    private TextView tvDate;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique_date);
        date = getIntent().getStringExtra("date");
        presenceVM = new ViewModelProvider(this).get(PresenceViewModel.class);
        tvDate = findViewById(R.id.tvDate);
        recyclerView = findViewById(R.id.recyclerViewAbsents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tvDate.setText("Absences du " + date);

        presenceVM.getPresencesByDate(date).observe(this, presences -> {
            List<Presence> absents = new ArrayList<>();
            for (Presence p : presences) if (!p.isPresent()) absents.add(p);
            AbsentAdapter adapter = new AbsentAdapter(absents);
            recyclerView.setAdapter(adapter);
        });
    }
}
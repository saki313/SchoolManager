package com.schoolmanager.ui.etudiant;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.schoolmanager.R;
import com.schoolmanager.ui.adapter.EtudiantListAdapter;
import com.schoolmanager.viewmodel.EtudiantViewModel;

public class EtudiantListFragment extends Fragment {

    private EtudiantViewModel viewModel;
    private EtudiantListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            // Test 1: inflation du layout
            View view = inflater.inflate(R.layout.fragment_etudiant_list, container, false);

            // Test 2: RecyclerView
            RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            // Test 3: Adapter
            adapter = new EtudiantListAdapter();
            recyclerView.setAdapter(adapter);

            // Test 4: FAB
            FloatingActionButton fab = view.findViewById(R.id.fab_add);
            fab.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), AddEditEtudiantActivity.class);
                startActivity(intent);
            });

            // Test 5: ViewModel
            viewModel = new ViewModelProvider(this).get(EtudiantViewModel.class);

            // Test 6: Observation des données
            viewModel.getAllEtudiants().observe(getViewLifecycleOwner(), etudiants -> {
                if (etudiants != null) {
                    adapter.setEtudiants(etudiants);
                }
            });

            // Test 7: Listeners
            adapter.setOnItemClickListener(etudiant -> {
                Intent intent = new Intent(getContext(), EtudiantDetailActivity.class);
                intent.putExtra("etudiant_id", etudiant.getId());
                startActivity(intent);
            });

            adapter.setOnDeleteClickListener(etudiant -> {
                viewModel.delete(etudiant, () -> {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() ->
                                Toast.makeText(getContext(), "Étudiant supprimé", Toast.LENGTH_SHORT).show()
                        );
                    }
                });
            });

            return view;

        } catch (Exception e) {
            // Afficher l'erreur à l'écran
            TextView errorTv = new TextView(getContext());
            errorTv.setText("ERREUR: " + e.getMessage() + "\n\nCause: " + e.getCause());
            errorTv.setTextSize(14);
            errorTv.setPadding(20, 20, 20, 20);
            errorTv.setBackgroundColor(0xFFFF0000);
            return errorTv;
        }
    }
}
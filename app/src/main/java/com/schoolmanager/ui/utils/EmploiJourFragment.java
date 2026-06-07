package com.schoolmanager.ui.utils;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.schoolmanager.R;

import java.util.List;

import com.schoolmanager.data.entity.Cours;
import com.schoolmanager.ui.adapter.CoursAdapter;

public class EmploiJourFragment extends Fragment {
    private static final String ARG_COURS = "cours";
    private List<Cours> coursList;

    public static EmploiJourFragment newInstance(List<Cours> cours) {
        EmploiJourFragment frag = new EmploiJourFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_COURS, (java.io.Serializable) cours);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emploi_jour, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewCours);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (getArguments() != null) {
            coursList = (List<Cours>) getArguments().getSerializable(ARG_COURS);
            CoursAdapter adapter = new CoursAdapter(coursList);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }
}
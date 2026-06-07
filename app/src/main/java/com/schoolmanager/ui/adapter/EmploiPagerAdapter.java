package com.schoolmanager.ui.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.schoolmanager.data.entity.Cours;
import com.schoolmanager.ui.utils.EmploiJourFragment;

public class EmploiPagerAdapter extends FragmentPagerAdapter {
    private final String[] jours = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"};
    private Map<String, List<Cours>> coursParJour = new HashMap<>();

    public EmploiPagerAdapter(@NonNull FragmentManager fm, List<Cours> tousCours) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        for (String j : jours) coursParJour.put(j, new ArrayList<>());
        for (Cours c : tousCours) {
            if (coursParJour.containsKey(c.getJour())) coursParJour.get(c.getJour()).add(c);
        }
    }

    @NonNull @Override
    public Fragment getItem(int position) {
        return EmploiJourFragment.newInstance(coursParJour.get(jours[position]));
    }

    @Override public int getCount() { return jours.length; }

    @Override public CharSequence getPageTitle(int position) { return jours[position]; }
}

package com.schoolmanager.ui.emploi;



import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.schoolmanager.R;

import com.schoolmanager.ui.adapter.EmploiPagerAdapter;
import com.schoolmanager.viewmodel.CoursViewModel;

public class EmploiTempsActivity extends AppCompatActivity {
    private CoursViewModel coursVM;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emploi_temps);

        coursVM = new ViewModelProvider(this).get(CoursViewModel.class);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);

        coursVM.getAllCours().observe(this, coursList -> {
            EmploiPagerAdapter adapter = new EmploiPagerAdapter(getSupportFragmentManager(), coursList);
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
        });
    }
}
package com.schoolmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import com.google.android.material.navigation.NavigationView;
import com.schoolmanager.ui.dashboard.DashboardFragment;
import com.schoolmanager.ui.emploi.EmploiTempsActivity;
import com.schoolmanager.ui.etudiant.EtudiantListFragment;

import com.schoolmanager.ui.note.AjouterCoursActivity;
import com.schoolmanager.ui.presence.PrisePresenceActivity;
import com.schoolmanager.ui.utils.NotificationHelper;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // DrawerLayout
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Chargement du fragment par défaut (Dashboard)
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new DashboardFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_dashboard);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new DashboardFragment())
                    .commit();
        } else if (id == R.id.nav_etudiants) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new EtudiantListFragment())
                    .commit();
        } else if (id == R.id.nav_presences) {
            startActivity(new Intent(this, PrisePresenceActivity.class));
        } else if (id == R.id.nav_emploi) {
            startActivity(new Intent(this, EmploiTempsActivity.class));
        } else if (id == R.id.nav_notifications) {
            // Version sécurisée qui ne plante pas
            try {
                NotificationHelper.planifierToutesNotifications(this);
            } catch (Exception e) {
                Toast.makeText(this, "Erreur notifications: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_ajouter_cours) {
            startActivity(new Intent(this, AjouterCoursActivity.class));
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
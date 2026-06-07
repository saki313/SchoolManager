package com.schoolmanager.ui.dashboard;



import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import com.schoolmanager.R;
import com.schoolmanager.ui.emploi.EmploiTempsActivity;
import com.schoolmanager.ui.etudiant.EtudiantListFragment;
import com.schoolmanager.ui.presence.PrisePresenceActivity;
import com.schoolmanager.ui.utils.NotificationHelper;


public class DashboardFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        LinearLayout cardEtudiants = view.findViewById(R.id.cardEtudiants);
        LinearLayout cardPresences = view.findViewById(R.id.cardPresences);
        LinearLayout cardEmploi = view.findViewById(R.id.cardEmploi);
        LinearLayout cardNotifications = view.findViewById(R.id.cardNotifications);

        cardEtudiants.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new EtudiantListFragment())
                    .addToBackStack(null)
                    .commit();
        });

        cardPresences.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), PrisePresenceActivity.class));
        });

        cardEmploi.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), EmploiTempsActivity.class));
        });

        cardNotifications.setOnClickListener(v -> {
            NotificationHelper.planifierToutesNotifications(getContext());
        });

        return view;
    }
}
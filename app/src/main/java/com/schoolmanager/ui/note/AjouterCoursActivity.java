package com.schoolmanager.ui.note;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.schoolmanager.R; // ← CORRIGÉ
import com.schoolmanager.data.database.AppDataBase;
import com.schoolmanager.data.entity.Cours;
import java.util.List;
import java.util.concurrent.Executors;

public class AjouterCoursActivity extends AppCompatActivity {

    private EditText etHeureDebut, etHeureFin, etMatiere, etSalle;
    private Spinner spinnerJour;
    private Button btnAjouter, btnSupprimer;
    private RecyclerView recyclerView;
    private CoursAdapter adapter;
    private AppDataBase db;  // ← CORRIGÉ


    private String jourSelectionne = "Lundi";
    private Cours coursSelectionne = null;

    private final String[] jours = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_cours);

        db = AppDataBase.getInstance(this);  // ← CORRIGÉ

        etHeureDebut = findViewById(R.id.etHeureDebut);
        etHeureFin = findViewById(R.id.etHeureFin);
        etMatiere = findViewById(R.id.etMatiere);
        etSalle = findViewById(R.id.etSalle);
        spinnerJour = findViewById(R.id.spinnerJour);
        btnAjouter = findViewById(R.id.btnAjouter);
        btnSupprimer = findViewById(R.id.btnSupprimer);
        recyclerView = findViewById(R.id.recyclerViewCours);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayAdapter<String> jourAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, jours);
        jourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJour.setAdapter(jourAdapter);
        spinnerJour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jourSelectionne = jours[position];
                chargerCours();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnAjouter.setOnClickListener(v -> ajouterOuModifierCours());
        btnSupprimer.setOnClickListener(v -> supprimerCours());

        chargerCours();
    }

    private void chargerCours() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Cours> coursList = db.coursDao().getByJour(jourSelectionne);
            runOnUiThread(() -> {
                if (adapter == null) {
                    adapter = new CoursAdapter(coursList, cours -> {
                        coursSelectionne = cours;
                        remplirFormulaire(cours);
                    });
                    recyclerView.setAdapter(adapter);
                } else {
                    adapter.setCoursList(coursList);
                }
            });
        });
    }

    private void remplirFormulaire(Cours cours) {
        etMatiere.setText(cours.getMatiere());
        etHeureDebut.setText(cours.getHeureDebut());
        etHeureFin.setText(cours.getHeureFin());
        etSalle.setText(cours.getSalle());
        btnAjouter.setText("Modifier");
    }

    private void ajouterOuModifierCours() {
        String matiere = etMatiere.getText().toString().trim();
        String heureDebut = etHeureDebut.getText().toString().trim();
        String heureFin = etHeureFin.getText().toString().trim();
        String salle = etSalle.getText().toString().trim();

        if (matiere.isEmpty() || heureDebut.isEmpty() || heureFin.isEmpty() || salle.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        if (coursSelectionne == null) {
            Cours nouveauCours = new Cours(jourSelectionne, heureDebut, heureFin, matiere, salle);
            Executors.newSingleThreadExecutor().execute(() -> {
                db.coursDao().insert(nouveauCours);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Cours ajouté", Toast.LENGTH_SHORT).show();
                    viderFormulaire();
                    chargerCours();
                });
            });
        } else {
            coursSelectionne.setMatiere(matiere);
            coursSelectionne.setHeureDebut(heureDebut);
            coursSelectionne.setHeureFin(heureFin);
            coursSelectionne.setSalle(salle);
            Executors.newSingleThreadExecutor().execute(() -> {
                db.coursDao().update(coursSelectionne);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Cours modifié", Toast.LENGTH_SHORT).show();
                    viderFormulaire();
                    chargerCours();
                });
            });
        }
    }

    private void supprimerCours() {
        if (coursSelectionne != null) {
            Executors.newSingleThreadExecutor().execute(() -> {
                db.coursDao().delete(coursSelectionne);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Cours supprimé", Toast.LENGTH_SHORT).show();
                    viderFormulaire();
                    chargerCours();
                });
            });
        } else {
            Toast.makeText(this, "Sélectionnez un cours à supprimer", Toast.LENGTH_SHORT).show();
        }
    }

    private void viderFormulaire() {
        etMatiere.setText("");
        etHeureDebut.setText("");
        etHeureFin.setText("");
        etSalle.setText("");
        btnAjouter.setText("Ajouter");
        coursSelectionne = null;
    }

    // Adapter interne pour la liste des cours
    static class CoursAdapter extends RecyclerView.Adapter<CoursAdapter.ViewHolder> {
        private List<Cours> coursList;
        private OnCoursClickListener listener;

        interface OnCoursClickListener {
            void onCoursClick(Cours cours);
        }

        CoursAdapter(List<Cours> list, OnCoursClickListener clickListener) {
            this.coursList = list;
            this.listener = clickListener;
        }

        void setCoursList(List<Cours> list) {
            this.coursList = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_2, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Cours c = coursList.get(position);
            holder.text1.setText(c.getMatiere());
            holder.text2.setText(c.getHeureDebut() + " - " + c.getHeureFin() + " | Salle: " + c.getSalle());
            holder.itemView.setOnClickListener(v -> listener.onCoursClick(c));
        }

        @Override
        public int getItemCount() {
            return coursList != null ? coursList.size() : 0;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView text1, text2;
            ViewHolder(@NonNull View itemView) {
                super(itemView);
                text1 = itemView.findViewById(android.R.id.text1);
                text2 = itemView.findViewById(android.R.id.text2);
            }
        }
    }
}
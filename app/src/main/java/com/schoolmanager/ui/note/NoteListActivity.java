package com.schoolmanager.ui.note;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.schoolmanager.R;

import com.schoolmanager.data.entity.Cours;
import com.schoolmanager.data.entity.Note;
import com.schoolmanager.ui.adapter.NoteAdapter;
import com.schoolmanager.viewmodel.NoteViewModel;

import java.util.ArrayList;
import java.util.List;

public class NoteListActivity extends AppCompatActivity {
    private int etudiantId;
    private NoteViewModel viewModel;
    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private Spinner spinnerCours;
    private EditText etNote;
    private Button btnAdd;
    private List<Cours> coursList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);


        etudiantId = getIntent().getIntExtra("etudiant_id", -1);
        if (etudiantId == -1) finish();

        viewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        recyclerView = findViewById(R.id.recyclerViewNotes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        spinnerCours = findViewById(R.id.spinnerCours);
        etNote = findViewById(R.id.etNote);
        btnAdd = findViewById(R.id.btnAddNote);

        // Charger les cours dans le Spinner
        viewModel.getAllCours().observe(this, courses -> {
            this.coursList = courses;  // ← Sauvegarder la liste
            List<String> coursNames = new ArrayList<>();
            for (Cours c : courses) {
                coursNames.add(c.getMatiere());
            }
            ArrayAdapter<String> coursAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_item,
                    coursNames
            );
            coursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCours.setAdapter(coursAdapter);
        });

        viewModel.getNotesWithCoursByEtudiant(etudiantId).observe(this, notes -> {
            adapter.setNotes(notes);
        });

        btnAdd.setOnClickListener(v -> addNote());

        adapter.setOnEditClickListener(note -> showEditDialog(note));
        adapter.setOnDeleteClickListener(note -> {
            viewModel.deleteNote(note, () -> runOnUiThread(() -> Toast.makeText(this, "Note supprimée", Toast.LENGTH_SHORT).show()));
        });
    }

    private void addNote() {
        int selectedPosition = spinnerCours.getSelectedItemPosition();
        if (selectedPosition < 0) {
            Toast.makeText(this, "Sélectionnez un cours", Toast.LENGTH_SHORT).show();
            return;
        }

        String selectedCours = spinnerCours.getSelectedItem().toString().trim();
        if (selectedCours.isEmpty()) {
            Toast.makeText(this, "Sélectionnez un cours", Toast.LENGTH_SHORT).show();
            return;
        }

        String noteStr = etNote.getText().toString().trim();
        if (noteStr.isEmpty()) {
            Toast.makeText(this, "Veuillez entrer une note", Toast.LENGTH_SHORT).show();
            return;
        }

        double valeur;
        try {
            valeur = Double.parseDouble(noteStr);
            if (valeur < 0 || valeur > 20) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Note invalide (0-20)", Toast.LENGTH_SHORT).show();
            return;
        }

        // Trouver l'ID du cours par son nom
        int idCours = -1;
        if (coursList != null) {
            for (Cours c : coursList) {
                if (c.getMatiere().equals(selectedCours)) {
                    idCours = c.getId();
                    break;
                }
            }
        }

        if (idCours == -1) {
            Toast.makeText(this, "Cours invalide", Toast.LENGTH_SHORT).show();
            return;
        }

        // selectedCours est toujours disponible ici
        Note note = new Note(etudiantId, idCours, selectedCours, valeur);
        viewModel.insertNote(note, () -> {
            runOnUiThread(() -> {
                etNote.setText("");
                spinnerCours.setSelection(0);
                Toast.makeText(this, "Note ajoutée", Toast.LENGTH_SHORT).show();
            });
        });
    }

    private void showEditDialog(Note note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_edit_note, null);
        EditText etNoteD = view.findViewById(R.id.etNote);
        etNoteD.setText(String.valueOf(note.getValeur()));

        builder.setView(view)
                .setTitle("Modifier la note")
                .setPositiveButton("Enregistrer", (dialog, which) -> {
                    try {
                        double valeur = Double.parseDouble(etNoteD.getText().toString());
                        if (valeur < 0 || valeur > 20) throw new NumberFormatException();
                        note.setValeur(valeur);
                        viewModel.updateNote(note, () -> runOnUiThread(() -> Toast.makeText(this, "Note modifiée", Toast.LENGTH_SHORT).show()));
                    } catch (NumberFormatException e) {
                        Toast.makeText(NoteListActivity.this, "Note invalide", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Annuler", null)
                .show();
    }
}
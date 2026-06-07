package com.schoolmanager.ui.note;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.schoolmanager.R;

import com.schoolmanager.data.entity.Note;
import com.schoolmanager.ui.adapter.NoteAdapter;
import com.schoolmanager.viewmodel.NoteViewModel;

public class NoteListActivity extends AppCompatActivity {
    private int etudiantId;
    private NoteViewModel viewModel;
    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private EditText etMatiere, etNote;
    private Button btnAdd;

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

        etMatiere = findViewById(R.id.etMatiere);
        etNote = findViewById(R.id.etNote);
        btnAdd = findViewById(R.id.btnAddNote);

        viewModel.getNotesByEtudiant(etudiantId).observe(this, notes -> {
            adapter.setNotes(notes);
        });

        btnAdd.setOnClickListener(v -> addNote());

        adapter.setOnEditClickListener(note -> showEditDialog(note));
        adapter.setOnDeleteClickListener(note -> {
            viewModel.deleteNote(note, () -> runOnUiThread(() -> Toast.makeText(this, "Note supprimée", Toast.LENGTH_SHORT).show()));
        });
    }

    private void addNote() {
        String matiere = etMatiere.getText().toString().trim();
        String noteStr = etNote.getText().toString().trim();
        if (matiere.isEmpty() || noteStr.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
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
        Note note = new Note(etudiantId, matiere, valeur);
        viewModel.insertNote(note, () -> {
            runOnUiThread(() -> {
                etMatiere.setText("");
                etNote.setText("");
                Toast.makeText(this, "Note ajoutée", Toast.LENGTH_SHORT).show();
            });
        });
    }

    private void showEditDialog(Note note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_edit_note, null);
        EditText etMatiereD = view.findViewById(R.id.etMatiere);
        EditText etNoteD = view.findViewById(R.id.etNote);
        etMatiereD.setText(note.getMatiere());
        etNoteD.setText(String.valueOf(note.getValeur()));

        builder.setView(view)
                .setTitle("Modifier la note")
                .setPositiveButton("Enregistrer", (dialog, which) -> {
                    note.setMatiere(etMatiereD.getText().toString());
                    note.setValeur(Double.parseDouble(etNoteD.getText().toString()));
                    viewModel.updateNote(note, () -> runOnUiThread(() -> Toast.makeText(this, "Note modifiée", Toast.LENGTH_SHORT).show()));
                })
                .setNegativeButton("Annuler", null)
                .show();
    }
}
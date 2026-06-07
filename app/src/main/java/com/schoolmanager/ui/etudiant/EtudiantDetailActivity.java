package com.schoolmanager.ui.etudiant;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.schoolmanager.R;

import com.schoolmanager.ui.note.NoteListActivity;
import com.schoolmanager.viewmodel.EtudiantViewModel;
import com.schoolmanager.viewmodel.NoteViewModel;


public class EtudiantDetailActivity extends AppCompatActivity {
    private int etudiantId;
    private TextView tvNomPrenom, tvMatricule, tvMoyenne;
    private ImageView ivPhoto;
    private Button btnNotes, btnPresences, btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiant_detail);

        etudiantId = getIntent().getIntExtra("etudiant_id", -1);
        if (etudiantId == -1) finish();

        tvNomPrenom = findViewById(R.id.tvNomPrenom);
        tvMatricule = findViewById(R.id.tvMatricule);
        tvMoyenne = findViewById(R.id.tvMoyenne);
        ivPhoto = findViewById(R.id.ivPhoto);
        btnNotes = findViewById(R.id.btnNotes);
        btnPresences = findViewById(R.id.btnPresences);
        btnEdit = findViewById(R.id.btnEdit);

        EtudiantViewModel etudiantVM = new ViewModelProvider(this).get(EtudiantViewModel.class);
        NoteViewModel noteVM = new ViewModelProvider(this).get(NoteViewModel.class);

        etudiantVM.getEtudiantById(etudiantId).observe(this, etudiant -> {
            if (etudiant != null) {
                tvNomPrenom.setText(etudiant.getNom() + " " + etudiant.getPrenom());
                tvMatricule.setText(etudiant.getMatricule());
                if (etudiant.getPhotoPath() != null)
                    Glide.with(this).load(etudiant.getPhotoPath()).into(ivPhoto);
            }
        });

        noteVM.getMoyenneByEtudiant(etudiantId).observe(this, moyenne -> {
            if (moyenne != null && moyenne > 0) {
                String texte = String.format("%.2f / 20", moyenne);
                tvMoyenne.setText(texte);
                if (moyenne < 10) tvMoyenne.setTextColor(getColor(R.color.red));
                else tvMoyenne.setTextColor(getColor(R.color.green));
            } else {
                tvMoyenne.setText("Aucune note");
            }
        });

        btnNotes.setOnClickListener(v -> {
            Intent intent = new Intent(this, NoteListActivity.class);
            intent.putExtra("etudiant_id", etudiantId);
            startActivity(intent);
        });

        btnPresences.setOnClickListener(v -> {
            Intent intent = new Intent(this, HistoriqueEtudiantActivity.class);
            intent.putExtra("etudiant_id", etudiantId);
            startActivity(intent);
        });

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddEditEtudiantActivity.class);
            intent.putExtra("etudiant_id", etudiantId);
            startActivity(intent);
        });
    }
}
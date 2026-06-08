package com.schoolmanager.ui.etudiant;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import com.schoolmanager.R;
import com.schoolmanager.data.entity.Etudiant;
import com.schoolmanager.viewmodel.EtudiantViewModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AddEditEtudiantActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA = 100;
    private static final int REQUEST_GALLERY = 101;
    private static final int PERMISSION_REQUEST = 200;

    private EditText etNom, etPrenom, etMatricule, etFiliere, etNiveau;
    private ImageView ivPhoto;
    private Button btnPrendrePhoto, btnChoisirPhoto, btnEnregistrer;
    private EtudiantViewModel viewModel;
    private int etudiantId = -1;
    private String currentPhotoPath = null;
    private Uri imageUri;
    private ImageView imageViewProfil;
    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    if (imageUri != null && ivPhoto != null) {
                        try {
                            // On récupère le Bitmap et on applique le traitement de sauvegarde local
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            ivPhoto.setImageBitmap(bitmap);
                            currentPhotoPath = sauvegarderPhoto(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(this, "Erreur lors du chargement de l'image", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_etudiant);

        viewModel = new ViewModelProvider(this).get(EtudiantViewModel.class);

        etNom = findViewById(R.id.etNom);
        etPrenom = findViewById(R.id.etPrenom);
        etMatricule = findViewById(R.id.etMatricule);
        etFiliere = findViewById(R.id.etFiliere);
        etNiveau = findViewById(R.id.etNiveau);
        ivPhoto = findViewById(R.id.ivPhoto);
        btnPrendrePhoto = findViewById(R.id.btnTakePhoto);
        btnChoisirPhoto = findViewById(R.id.btnChoisirPhoto);
        btnEnregistrer = findViewById(R.id.btnSave);

        // Vérifier si c'est une modification
        etudiantId = getIntent().getIntExtra("etudiant_id", -1);
        if (etudiantId != -1) {
            setTitle("Modifier étudiant");
            viewModel.getEtudiantById(etudiantId).observe(this, etudiant -> {
                if (etudiant != null) {
                    etNom.setText(etudiant.getNom());
                    etPrenom.setText(etudiant.getPrenom());
                    etMatricule.setText(etudiant.getMatricule());
                    etFiliere.setText(etudiant.getFiliere());
                    etNiveau.setText(etudiant.getNiveau());
                    currentPhotoPath = etudiant.getPhotoPath();
                    if (currentPhotoPath != null) {
                        File imgFile = new File(currentPhotoPath);
                        if (imgFile.exists()) {
                            ivPhoto.setImageBitmap(BitmapFactory.decodeFile(currentPhotoPath));
                        }
                    }
                }
            });
        } else {
            setTitle("Ajouter étudiant");
        }

        // Boutons pour la photo
        btnPrendrePhoto.setOnClickListener(v -> verifierPermissionCamera());
        btnChoisirPhoto.setOnClickListener(v -> ouvrirGalerie());
        btnEnregistrer.setOnClickListener(v -> enregistrerEtudiant());

        ivPhoto.setOnClickListener(v -> ouvrirGalerie());
    }

    private void verifierPermissionCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
            } else {
                ouvrirCamera();
            }
        } else {
            ouvrirCamera();
        }
    }

    private void ouvrirCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Photo_" + System.currentTimeMillis());
        values.put(MediaStore.Images.Media.DESCRIPTION, "Photo étudiant");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, REQUEST_CAMERA);
    }

    private void ouvrirGalerie() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        try {
            galleryLauncher.launch(galleryIntent);
        } catch (Exception e) {
            Toast.makeText(this, "Impossible d'ouvrir la galerie", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            ouvrirCamera();
        } else {
            Toast.makeText(this, "Permission caméra refusée", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA && imageUri != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    ivPhoto.setImageBitmap(bitmap);
                    currentPhotoPath = sauvegarderPhoto(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Erreur lors de la capture", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private String sauvegarderPhoto(Bitmap bitmap) {
        String fileName = "photo_" + System.currentTimeMillis() + ".jpg";
        File directory = getFilesDir();
        File file = new File(directory, fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void enregistrerEtudiant() {
        String nom = etNom.getText().toString().trim();
        String prenom = etPrenom.getText().toString().trim();
        String matricule = etMatricule.getText().toString().trim();
        String filiere = etFiliere.getText().toString().trim();
        String niveau = etNiveau.getText().toString().trim();

        if (nom.isEmpty() || prenom.isEmpty() || matricule.isEmpty() || filiere.isEmpty() || niveau.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        Etudiant etudiant = new Etudiant(nom, prenom, matricule, filiere, niveau, currentPhotoPath);

        if (etudiantId == -1) {
            viewModel.insert(etudiant, () -> {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Étudiant ajouté", Toast.LENGTH_SHORT).show();
                    finish();
                });
            });
        } else {
            etudiant.setId(etudiantId);
            viewModel.update(etudiant, () -> {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Étudiant modifié", Toast.LENGTH_SHORT).show();
                    finish();
                });
            });
        }
    }
}
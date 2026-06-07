package com.schoolmanager.data.entity;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entité représentant un étudiant
 */
@Entity(tableName = "etudiant")
public class Etudiant {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nom;
    private String prenom;
    private String matricule;
    private String photoPath; // chemin absolu de la photo

    public Etudiant(String nom, String prenom, String matricule, String photoPath) {
        this.nom = nom;
        this.prenom = prenom;
        this.matricule = matricule;
        this.photoPath = photoPath;
    }

    // Getters et setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getMatricule() { return matricule; }
    public void setMatricule(String matricule) { this.matricule = matricule; }
    public String getPhotoPath() { return photoPath; }
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }
}

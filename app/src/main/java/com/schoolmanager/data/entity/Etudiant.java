package com.schoolmanager.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entité représentant un étudiant
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "etudiant")
public class Etudiant {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nom;
    private String prenom;
    private String matricule;
    private String filiere;
    private String niveau;
    private String photoPath; // chemin absolu de la photo

    public Etudiant(String nom, String prenom, String matricule, String filiere, String niveau, String photoPath) {
        this.nom = nom;
        this.prenom = prenom;
        this.matricule = matricule;
        this.filiere = filiere;
        this.niveau = niveau;
        this.photoPath = photoPath;
    }
}

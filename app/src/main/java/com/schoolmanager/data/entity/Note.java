package com.schoolmanager.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "note",
        foreignKeys = @ForeignKey(entity = Etudiant.class,
                parentColumns = "id",
                childColumns = "idEtudiant",
                onDelete = ForeignKey.CASCADE))
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int idEtudiant;
    private String matiere;
    private double valeur;

    public Note(int idEtudiant, String matiere, double valeur) {
        this.idEtudiant = idEtudiant;
        this.matiere = matiere;
        this.valeur = valeur;
    }

    // Getters et setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdEtudiant() { return idEtudiant; }
    public void setIdEtudiant(int idEtudiant) { this.idEtudiant = idEtudiant; }
    public String getMatiere() { return matiere; }
    public void setMatiere(String matiere) { this.matiere = matiere; }
    public double getValeur() { return valeur; }
    public void setValeur(double valeur) { this.valeur = valeur; }
}
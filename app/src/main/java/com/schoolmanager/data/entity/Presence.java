package com.schoolmanager.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "presence",
        foreignKeys = @ForeignKey(entity = Etudiant.class,
                parentColumns = "id",
                childColumns = "idEtudiant",
                onDelete = ForeignKey.CASCADE))
public class Presence {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int idEtudiant;
    private String date; // format YYYY-MM-DD
    private boolean present;

    public Presence(int idEtudiant, String date, boolean present) {
        this.idEtudiant = idEtudiant;
        this.date = date;
        this.present = present;
    }

    // Getters et setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdEtudiant() { return idEtudiant; }
    public void setIdEtudiant(int idEtudiant) { this.idEtudiant = idEtudiant; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public boolean isPresent() { return present; }
    public void setPresent(boolean present) { this.present = present; }
}

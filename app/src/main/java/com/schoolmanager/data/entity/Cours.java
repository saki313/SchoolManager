package com.schoolmanager.data.entity;



import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cours")
public class Cours {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String jour;       // Lundi, Mardi...
    private String heureDebut; // HH:MM
    private String heureFin;
    private String matiere;
    private String salle;

    public Cours(String jour, String heureDebut, String heureFin, String matiere, String salle) {
        this.jour = jour;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.matiere = matiere;
        this.salle = salle;
    }

    // Getters et setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getJour() { return jour; }
    public void setJour(String jour) { this.jour = jour; }
    public String getHeureDebut() { return heureDebut; }
    public void setHeureDebut(String heureDebut) { this.heureDebut = heureDebut; }
    public String getHeureFin() { return heureFin; }
    public void setHeureFin(String heureFin) { this.heureFin = heureFin; }
    public String getMatiere() { return matiere; }
    public void setMatiere(String matiere) { this.matiere = matiere; }
    public String getSalle() { return salle; }
    public void setSalle(String salle) { this.salle = salle; }
}

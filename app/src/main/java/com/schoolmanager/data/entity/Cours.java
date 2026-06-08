package com.schoolmanager.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}

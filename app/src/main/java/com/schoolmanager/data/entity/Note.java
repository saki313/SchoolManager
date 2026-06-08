package com.schoolmanager.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "note",
        foreignKeys = {
                @ForeignKey(
                        entity = Etudiant.class,
                        parentColumns = "id",
                        childColumns = "idEtudiant",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(entity = Cours.class,
                        parentColumns = "id",
                        childColumns = "idCours",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int idEtudiant;
    private int idCours;
    private String matiere;  // ← Cache du nom du cours
    private double valeur;

    public Note(int idEtudiant, int idCours, String matiere, double valeur) {
        this.idEtudiant = idEtudiant;
        this.idCours = idCours;
        this.matiere = matiere;
        this.valeur = valeur;
    }
}

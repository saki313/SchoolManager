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
}

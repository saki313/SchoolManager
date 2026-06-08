package com.schoolmanager.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

public class NoteWithCours {
    @Embedded
    public Note note;

    @Relation(
            parentColumn = "idCours", // Le champ clé étrangère dans ta table Note
            entityColumn = "id"       // La clé primaire dans ta table Cours
    )
    public Cours cours;
}
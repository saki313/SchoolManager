package com.schoolmanager.data.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;


import java.util.List;

import com.schoolmanager.data.entity.Cours;
import com.schoolmanager.data.entity.Note;
import com.schoolmanager.data.entity.NoteWithCours;

@Dao
public interface NoteDao {
    @Insert
    long insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM note WHERE idEtudiant = :idEtudiant ORDER BY idCours")
    LiveData<List<Note>> getNotesByEtudiant(int idEtudiant);

    @Transaction
    @Query("SELECT * FROM note WHERE idEtudiant = :idEtudiant")
    LiveData<List<NoteWithCours>> getNotesWithCoursByEtudiant(int idEtudiant);

    @Query("SELECT * FROM cours")
    List<Cours> getAllCoursSynchrone();

    @Query("SELECT AVG(valeur) FROM note WHERE idEtudiant = :idEtudiant")
    LiveData<Double> getMoyenneByEtudiant(int idEtudiant);

    @Transaction
    @Query("SELECT * FROM note")
    LiveData<List<NoteWithCours>> getAllNotesWithCours();
}

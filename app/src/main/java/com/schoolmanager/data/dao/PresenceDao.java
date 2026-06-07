package com.schoolmanager.data.dao;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

import com.schoolmanager.data.entity.Presence;

@Dao
public interface PresenceDao {
    @Insert
    long insert(Presence presence);

    @Update
    void update(Presence presence);

    @Delete
    void delete(Presence presence);

    @Query("SELECT * FROM presence WHERE idEtudiant = :idEtudiant ORDER BY date DESC")
    LiveData<List<Presence>> getPresencesByEtudiant(int idEtudiant);

    @Query("SELECT * FROM presence WHERE date = :date")
    LiveData<List<Presence>> getPresencesByDate(String date);

    @Query("SELECT COUNT(*) FROM presence WHERE idEtudiant = :idEtudiant AND present = 0")
    LiveData<Integer> getNbAbsences(int idEtudiant);

    @Query("SELECT DISTINCT date FROM presence ORDER BY date DESC")
    LiveData<List<String>> getAllDates();

    // Pour la prise de présence : on supprime les anciennes d'une date avant d'insérer
    @Query("DELETE FROM presence WHERE date = :date")
    void deleteByDate(String date);
}
package com.schoolmanager.data.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

import com.schoolmanager.data.entity.Etudiant;

@Dao
public interface EtudiantDao {
    @Insert
    long insert(Etudiant etudiant);

    @Update
    void update(Etudiant etudiant);

    @Delete
    void delete(Etudiant etudiant);

    @Query("SELECT * FROM etudiant ORDER BY nom, prenom")
    LiveData<List<Etudiant>> getAll();          // LiveData pour observation automatique

    @Query("SELECT * FROM etudiant WHERE id = :id")
    LiveData<Etudiant> getById(int id);
}
package com.schoolmanager.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.schoolmanager.data.entity.Cours;
import java.util.List;

@Dao
public interface CoursDao {

    @Insert
    long insert(Cours cours);

    @Update
    void update(Cours cours);

    @Delete
    void delete(Cours cours);

    @Query("SELECT * FROM cours ORDER BY CASE jour " +
            "WHEN 'Lundi' THEN 1 WHEN 'Mardi' THEN 2 WHEN 'Mercredi' THEN 3 " +
            "WHEN 'Jeudi' THEN 4 WHEN 'Vendredi' THEN 5 WHEN 'Samedi' THEN 6 END, heureDebut")
    LiveData<List<Cours>> getAll();

    @Query("SELECT * FROM cours WHERE jour = :jour ORDER BY heureDebut")
    List<Cours> getByJour(String jour);

    @Query("DELETE FROM cours")
    void deleteAll();
}
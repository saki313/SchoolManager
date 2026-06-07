package com.schoolmanager.data.repository;



import android.content.Context;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.schoolmanager.data.dao.CoursDao;
import com.schoolmanager.data.dao.EtudiantDao;
import com.schoolmanager.data.dao.NoteDao;
import com.schoolmanager.data.dao.PresenceDao;
import com.schoolmanager.data.database.AppDataBase;
import com.schoolmanager.data.entity.Cours;
import com.schoolmanager.data.entity.Etudiant;
import com.schoolmanager.data.entity.Note;
import com.schoolmanager.data.entity.Presence;

/**
 * Repository : point d'accès unique aux données (gère les opérations asynchrones)
 */
public class SchoolRepository {
    private final EtudiantDao etudiantDao;
    private final NoteDao noteDao;
    private final PresenceDao presenceDao;
    private final CoursDao coursDao;
    private final ExecutorService executor;

    public SchoolRepository(Context context) {
        AppDataBase db = AppDataBase.getInstance(context);
        etudiantDao = db.etudiantDao();
        noteDao = db.noteDao();
        presenceDao = db.presenceDao();
        coursDao = db.coursDao();
        executor = Executors.newSingleThreadExecutor();
    }

    // --- Etudiant ---
    public void insertEtudiant(Etudiant etudiant, Runnable callback) {
        executor.execute(() -> {
            etudiantDao.insert(etudiant);
            if (callback != null) callback.run();
        });
    }

    public void updateEtudiant(Etudiant etudiant, Runnable callback) {
        executor.execute(() -> {
            etudiantDao.update(etudiant);
            if (callback != null) callback.run();
        });
    }

    public void deleteEtudiant(Etudiant etudiant, Runnable callback) {
        executor.execute(() -> {
            etudiantDao.delete(etudiant);
            if (callback != null) callback.run();
        });
    }

    public LiveData<List<Etudiant>> getAllEtudiants() {
        return etudiantDao.getAll();
    }

    public LiveData<Etudiant> getEtudiantById(int id) {
        return etudiantDao.getById(id);
    }

    // --- Note ---
    public void insertNote(Note note, Runnable callback) {
        executor.execute(() -> {
            noteDao.insert(note);
            if (callback != null) callback.run();
        });
    }

    public void updateNote(Note note, Runnable callback) {
        executor.execute(() -> {
            noteDao.update(note);
            if (callback != null) callback.run();
        });
    }

    public void deleteNote(Note note, Runnable callback) {
        executor.execute(() -> {
            noteDao.delete(note);
            if (callback != null) callback.run();
        });
    }

    public LiveData<List<Note>> getNotesByEtudiant(int idEtudiant) {
        return noteDao.getNotesByEtudiant(idEtudiant);
    }

    public LiveData<Double> getMoyenneByEtudiant(int idEtudiant) {
        return noteDao.getMoyenneByEtudiant(idEtudiant);
    }

    // --- Presence ---
    public void savePresences(String date, List<Presence> presences, Runnable callback) {
        executor.execute(() -> {
            presenceDao.deleteByDate(date);
            for (Presence p : presences) {
                presenceDao.insert(p);
            }
            if (callback != null) callback.run();
        });
    }

    public LiveData<List<Presence>> getPresencesByEtudiant(int idEtudiant) {
        return presenceDao.getPresencesByEtudiant(idEtudiant);
    }

    public LiveData<List<Presence>> getPresencesByDate(String date) {
        return presenceDao.getPresencesByDate(date);
    }

    public LiveData<Integer> getNbAbsences(int idEtudiant) {
        return presenceDao.getNbAbsences(idEtudiant);
    }

    public LiveData<List<String>> getAllDatesPresence() {
        return presenceDao.getAllDates();
    }

    // --- Cours ---
    public void insertCours(Cours cours, Runnable callback) {
        executor.execute(() -> {
            coursDao.insert(cours);
            if (callback != null) callback.run();
        });
    }

    public LiveData<List<Cours>> getAllCours() {
        return coursDao.getAll();
    }
}

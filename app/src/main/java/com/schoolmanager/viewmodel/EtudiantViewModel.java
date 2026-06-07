package com.schoolmanager.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import com.schoolmanager.data.entity.Etudiant;
import com.schoolmanager.data.repository.SchoolRepository;

public class EtudiantViewModel extends AndroidViewModel {
    private final SchoolRepository repository;
    private final LiveData<List<Etudiant>> allEtudiants;

    public EtudiantViewModel(Application application) {
        super(application);
        repository = new SchoolRepository(application);
        allEtudiants = repository.getAllEtudiants();
    }

    public LiveData<List<Etudiant>> getAllEtudiants() { return allEtudiants; }
    public LiveData<Etudiant> getEtudiantById(int id) { return repository.getEtudiantById(id); }

    public void insert(Etudiant etudiant, Runnable callback) { repository.insertEtudiant(etudiant, callback); }
    public void update(Etudiant etudiant, Runnable callback) { repository.updateEtudiant(etudiant, callback); }
    public void delete(Etudiant etudiant, Runnable callback) { repository.deleteEtudiant(etudiant, callback); }
}
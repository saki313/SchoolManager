package com.schoolmanager.viewmodel;



import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

import com.schoolmanager.data.entity.Cours;
import com.schoolmanager.data.repository.SchoolRepository;

public class CoursViewModel extends AndroidViewModel {
    private final SchoolRepository repository;
    private final LiveData<List<Cours>> allCours;

    public CoursViewModel(Application application) {
        super(application);
        repository = new SchoolRepository(application);
        allCours = repository.getAllCours();
    }

    public LiveData<List<Cours>> getAllCours() { return allCours; }

    public void insertCours(Cours cours, Runnable callback) {
        repository.insertCours(cours, callback);
    }
    public void updateCours(Cours cours, Runnable callback) {
        repository.updateCours(cours, callback);
    }
    public void deleteCours(Cours cours, Runnable callback) {
        repository.deleteCours(cours, callback);
    }
    public List<Cours> getByJour(String jour) {
        return repository.getByJour(jour);  // À implémenter dans Repository
    }
}
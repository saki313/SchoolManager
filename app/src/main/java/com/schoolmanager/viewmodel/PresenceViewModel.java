package com.schoolmanager.viewmodel;


import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

import com.schoolmanager.data.entity.Presence;
import com.schoolmanager.data.repository.SchoolRepository;

public class PresenceViewModel extends AndroidViewModel {
    private final SchoolRepository repository;

    public PresenceViewModel(Application application) {
        super(application);
        repository = new SchoolRepository(application);
    }

    public void savePresences(String date, List<Presence> presences, Runnable callback) {
        repository.savePresences(date, presences, callback);
    }

    public LiveData<List<Presence>> getPresencesByEtudiant(int idEtudiant) {
        return repository.getPresencesByEtudiant(idEtudiant);
    }

    public LiveData<List<Presence>> getPresencesByDate(String date) {
        return repository.getPresencesByDate(date);
    }

    public LiveData<Integer> getNbAbsences(int idEtudiant) {
        return repository.getNbAbsences(idEtudiant);
    }

    public LiveData<List<String>> getAllDates() {
        return repository.getAllDatesPresence();
    }
}
package com.schoolmanager.viewmodel;



import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

import com.schoolmanager.data.entity.Note;
import com.schoolmanager.data.repository.SchoolRepository;

public class NoteViewModel extends AndroidViewModel {
    private final SchoolRepository repository;

    public NoteViewModel(Application application) {
        super(application);
        repository = new SchoolRepository(application);
    }

    public LiveData<List<Note>> getNotesByEtudiant(int idEtudiant) {
        return repository.getNotesByEtudiant(idEtudiant);
    }

    public LiveData<Double> getMoyenneByEtudiant(int idEtudiant) {
        return repository.getMoyenneByEtudiant(idEtudiant);
    }

    public void insertNote(Note note, Runnable callback) { repository.insertNote(note, callback); }
    public void updateNote(Note note, Runnable callback) { repository.updateNote(note, callback); }
    public void deleteNote(Note note, Runnable callback) { repository.deleteNote(note, callback); }
}